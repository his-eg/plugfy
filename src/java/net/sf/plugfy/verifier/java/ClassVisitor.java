/*
 *  Copyright 2013
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may use this file in compliance with the Apache License, Version 2.0.
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.sf.plugfy.verifier.java;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.violations.JavaViolation;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.EmptyVisitor;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.ReferenceType;
import org.apache.bcel.generic.Type;
import org.apache.bcel.generic.TypedInstruction;

/**
 * visits instructions
 *
 * @author hendrik
 */
class ClassVisitor extends EmptyVisitor {

    private static final String BCEL_CLASS_NOT_FOUND_EXCEPTION_START = "Exception while looking for class ";
    
    private static final List<String> JAVA_LANG_OBJECT_METHODS_WITH_OBJECT_RETURN_TYPE = Arrays.asList("clone", "getClass", "toString");
    
    private final JavaClass javaClass;
    private final ConstantPoolGen cpg;
    private final VerificationContext context;

    /**
     * class visitor
     *
     * @param javaClass javaClass
     * @param cpg ConstantPoolGen
     * @param verificationContext VerificationContext
     */
    ClassVisitor(final JavaClass javaClass, final ConstantPoolGen cpg, final VerificationContext verificationContext) {
        this.javaClass = javaClass;
        this.cpg = cpg;
        this.context = verificationContext;
    }

    @Override
    public void visitInvokeInstruction(final InvokeInstruction invokeInstruction) {
        final Type type = invokeInstruction.getReferenceType(this.cpg);
        if (! (type instanceof ObjectType)) {
            return;
        }

        final String targetClassName = ((ObjectType) type).getClassName();
        final String methodName = invokeInstruction.getMethodName(this.cpg);
        final Type[] argumentTypes = invokeInstruction.getArgumentTypes(this.cpg);
        final Type returnType = invokeInstruction.getReturnType(this.cpg);
        final String sourceType = this.javaClass.getClassName();
        
        if (methodName.equals("<clinit>")) {
            // the class initializer always exists
            return;
        }
        
        try {
            // 1. check existence of target class exist
            final JavaClass targetJavaClass = this.context.getRepository().loadClass(targetClassName);
            // 2. check interfaces and superclasses of this class
            final List<JavaClass> allInterfaces = Arrays.asList(this.javaClass.getAllInterfaces());
            final List<JavaClass> allSuperClasses = Arrays.asList(this.javaClass.getSuperClasses());

            // Do we have a lambda expression? These are characterized by bcel as follows:
            // * targetClassName = java.lang.Object
            // * returnType is of class ObjectType and designates the functional interface (e.g. Predicate)
            // * methodName = the name of the functional method of the functional interface (e.g. "test" for java.util.function.Predicate)
            // * argumentTypes = additional arguments in the r.h.s. of the lambda expression (not those of the functional method!)
            // Functional interfaces from java.* are annotated as @FunctionalInterface and we could identify them easily
            // getting such annotations via returnTypeJavaClass.getAnnotationEntries().
            // But since Guava functional interfaces do not have that annotation before guava-21.0, we need another test:
            // The only standard methods of java.lang.Object that return objects instead of primitive types are
            // Object clone(), Class<?> getClass() and String toString(). Any other cases should be lambda expressions.
            
            if (targetClassName.equals("java.lang.Object") && (returnType instanceof ObjectType) & !JAVA_LANG_OBJECT_METHODS_WITH_OBJECT_RETURN_TYPE.contains("methodName")) {
                // Found lambda expression!
                final String returnTypeClassName = ((ObjectType) returnType).getClassName();
                final JavaClass returnTypeJavaClass = this.context.getRepository().loadClass(returnTypeClassName);
                // Unfortunately, so far there seems to be no way to obtain the return type and argument types
                // of the functional method from BCEL -> we can only check if a functional method with the given name exists.
                // TODO: Determine argumentTypes and returnType of the functional method when BCEL is capable to do it
                boolean lambdaInheritance = returnTypeJavaClass.equals(javaClass)
                                || allInterfaces.contains(returnTypeJavaClass)
                                || allSuperClasses.contains(returnTypeJavaClass);
                if (!this.findMethodRecursive(returnTypeClassName, methodName, null, null, lambdaInheritance)) {
                    this.context.getResult().add(JavaViolation.create(sourceType, returnTypeClassName, methodName));
                }
            } else {
                // No lambda expression, do standard check
                final boolean inheritance = targetJavaClass.equals(javaClass)
                                || allInterfaces.contains(targetJavaClass)
                                || allSuperClasses.contains(targetJavaClass);
                if (!this.findMethodRecursive(targetClassName, methodName, argumentTypes, returnType, inheritance)) {
                    this.context.getResult().add(JavaViolation.create(sourceType, targetClassName, methodName));
                }
            }
        } catch (final ClassNotFoundException e) {
            String className = getClassNotFound(e);
            //System.out.println("Class " + className + " could not be read. You might need to add it to the class path!");
            this.context.getResult().add(JavaViolation.create(sourceType, className, null));
        }
    }
    
    /**
     * Derive the name of the class that could not be found.
     * @param e
     * @return name of the class not found
     */
    private String getClassNotFound(ClassNotFoundException e) {
        final String msg = e.getMessage();
        Throwable cause = e.getCause();
        if (msg.startsWith(BCEL_CLASS_NOT_FOUND_EXCEPTION_START) && cause!=null && cause instanceof IOException) {
            // ClassNotFoundException thrown by BCELs MemorySensitiveClassPathRepository.loadClass(String className)
            // or ClassPathRepository.loadClass(String className). In that case the Exception message starts with
            // "Exception while looking for class " and has a wrapped in IOException.
            final String msgPart1 = msg.substring(0, msg.indexOf(':'));
            return msgPart1.substring(msgPart1.lastIndexOf(' ')+1);
        } 
        // else: ClassNotFoundException thrown by BCELs ClassLoaderRepository.loadClass(String className)
        return msg.substring(0, msg.indexOf(' '));
    }

    /**
     * finds a method
     *
     * @param className     name of class
     * @param methodName    name of method
     * @param argumentTypes types of arguments (not checked if null)
     * @param returnType    return type (not checked if null)
     * @param isSubclass    is the caller a subclass
     * @return true, if the method was found, false otherwise
     * @throws ClassNotFoundException if a super class is not found
     */
    private boolean findMethodRecursive(final String className, final String methodName, final Type[] argumentTypes, final Type returnType, final boolean isSubclass) throws ClassNotFoundException {
        final JavaClass targetJavaClass = this.context.getRepository().loadClass(className);
        if (this.findMethod(targetJavaClass, methodName, argumentTypes, returnType, isSubclass)) {
            return true;
        }
        for (final JavaClass entry : targetJavaClass.getSuperClasses()) {
            if (this.findMethod(entry, methodName, argumentTypes, returnType, isSubclass)) {
                return true;
            }
        }
        for (final JavaClass entry : targetJavaClass.getAllInterfaces()) {
            if (this.findMethod(entry, methodName, argumentTypes, returnType, isSubclass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * finds a method
     *
     * @param targetJavaClass targetJavaClass
     * @param methodName    name of method
     * @param argumentTypes types of arguments (not checked if null)
     * @param returnType    return type (not checked if null)
     * @param isSubClass    is the caller a subclass
     * @return true, if the method was found, false otherwise
     * @throws ClassNotFoundException if a super class is not found
     */
    private boolean findMethod(final JavaClass targetJavaClass, final String methodName, final Type[] argumentTypes, final Type returnType, final boolean isSubClass) throws ClassNotFoundException {
        for (final Method method : targetJavaClass.getMethods()) {
            if (!method.getName().equals(methodName)) {
                continue;
            }

            if (!isAccessible(targetJavaClass, method, isSubClass)) {
                continue;
            }

            if (returnType!=null) {
                // Check return type: Refinement is allowed for overriden methods.
                final Type declaredReturnType = method.getReturnType();
                if (!declaredReturnType.equals(returnType)) {
                    if (!(declaredReturnType instanceof ReferenceType)) {
                        // basic return type -> no refinement possible -> since the names do not equal, they are incompatible
                        continue;
                    }
                    // Refinement? declaredReturnType (e.g. String) assignable to expected returnType (e.g. Object) would be ok
                    if (!((ReferenceType) declaredReturnType).isAssignmentCompatibleWith(returnType)) {
                        // the return type of the method is not assignable to the expected returnType -> they are incompatible
                        continue;
                    }
                }
            }
            
            if (argumentTypes!=null) {
                // check arguments
                final Type[] declaredArgumentTypes = method.getArgumentTypes();
                if (declaredArgumentTypes.length != argumentTypes.length) {
                    continue;
                }
                boolean allEqual = true;
                for (int i = 0; i < argumentTypes.length; i++) {
                    if (!argumentTypes[i].equals(declaredArgumentTypes[i])) {
                        // TODO: argumentTypes[i] assignable to declaredArgumentTypes[i] would be ok, too ?
                        allEqual = false;
                        break;
                    }
                }
                if (!allEqual) continue;
            }
            
            // hey, all passed!
            return true;
        }
        return false;
    }

    /**
     * is the method accessible
     *
     * @param targetJavaClass invoked class
     * @param method          invoked method
     * @param isSubClass      is it a subclass
     * @return true, if the method is accessible; false otherwise
     */
    private boolean isAccessible(JavaClass targetJavaClass, Method method, boolean isSubClass) {
        if (method.isPublic()) {
            return true;
        }

        if (this.javaClass.equals(targetJavaClass)) {
            return true;
        }

        if (method.isPrivate()) {
            return false;
        }

        // default or package visible
        if (javaClass.getPackageName().equals(targetJavaClass.getPackageName())) {
            return true;
        }

        return method.isProtected() && isSubClass;
    }

    @Override
    public void visitTypedInstruction(final TypedInstruction typedinstruction) {
        Type type = typedinstruction.getType(this.cpg);
        while (type instanceof ArrayType) {
            type = ((ArrayType) type).getElementType();
        }

        if (type instanceof ObjectType) {
            SignatureUtil.checkSignatureDependencies(this.context.getRepository(), this.context.getResult(), type.getSignature(), this.javaClass.getClassName());
        }
    }
}
