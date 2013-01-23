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

import java.util.Arrays;

import net.sf.plugfy.verifier.VerificationContext;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.EmptyVisitor;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;
import org.apache.bcel.generic.TypedInstruction;

/**
 * visits instructions
 *
 * @author hendrik
 */
class ClassVisitor extends EmptyVisitor {

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
    ClassVisitor(JavaClass javaClass, ConstantPoolGen cpg, VerificationContext verificationContext) {
        this.javaClass = javaClass;
        this.cpg = cpg;
        this.context = verificationContext;
    }

    @Override
    public void visitInvokeInstruction(final InvokeInstruction invokeInstruction) {
        Type type = invokeInstruction.getReferenceType(this.cpg);
        if (! (type instanceof ObjectType)) {
            return;
        }

        String targetClass = ((ObjectType) type).getClassName();
        String methodName = invokeInstruction.getMethodName(this.cpg);
        Type[] argumentTypes = invokeInstruction.getArgumentTypes(this.cpg);
        Type returnType = invokeInstruction.getReturnType(this.cpg);

        if (methodName.equals("<clinit>")) {
            // the class initializer always exists
            return;
        }

        try {
            JavaClass targetJavaClass = context.getRepository().loadClass(targetClass);
            boolean inheritance = Arrays.asList(javaClass.getAllInterfaces()).contains(targetJavaClass)
                               || Arrays.asList(javaClass.getSuperClasses()).contains(targetJavaClass);
            if (!findMethodRecursive(targetClass, methodName, argumentTypes, returnType, inheritance)) {
                context.getResult().add(targetClass + " . " + methodName);
            }
        } catch (ClassNotFoundException e) {
            String msg = e.getMessage();
            context.getResult().add(msg.substring(0, msg.indexOf(' ')));
        }
    }

    /**
     * finds a method
     *
     * @param className     name of class
     * @param methodName    name of method
     * @param argumentTypes types of arguments
     * @param returnType    return type
     * @param isSubclass    is the caller a subclass
     * @return true, if the method was found, false otherwise
     * @throws ClassNotFoundException if a super class is not found
     */
    private boolean findMethodRecursive(String className, String methodName, Type[] argumentTypes, Type returnType, boolean isSubclass) throws ClassNotFoundException {
        JavaClass targetJavaClass = context.getRepository().loadClass(className);
        if (findMethod(targetJavaClass, methodName, argumentTypes, returnType, isSubclass)) {
            return true;
        }
        for (JavaClass entry : targetJavaClass.getSuperClasses()) {
            if (findMethod(entry, methodName, argumentTypes, returnType, isSubclass)) {
                return true;
            }
        }
        for (JavaClass entry : targetJavaClass.getAllInterfaces()) {
            if (findMethod(entry, methodName, argumentTypes, returnType, isSubclass)) {
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
     * @param argumentTypes types of arguments
     * @param returnType    return type
     * @param isSubclass    is the caller a subclass
     * @return true, if the method was found, false otherwise
     * @throws ClassNotFoundException if a super class is not found
     */
    private boolean findMethod(JavaClass targetJavaClass, String methodName, Type[] argumentTypes, Type returnType, boolean isSubclass) throws ClassNotFoundException {
        for (Method method : targetJavaClass.getMethods()) {
            if (!method.getName().equals(methodName)) {
                continue;
            }

            if (method.isPrivate() && (!javaClass.equals(targetJavaClass))) {
                continue;
            }
            // TODO: protected and package-protected

            Type declaredReturnType = method.getReturnType();

            // TODO: refinement is allowed for overriden messages
            if (!declaredReturnType.equals(returnType)) {
                continue;
            }

            // check arguments
            Type[] declaredArgumentTypes = method.getArgumentTypes();
            if (declaredArgumentTypes.length != argumentTypes.length) {
                continue;
            }
            for (int i = 0; i < argumentTypes.length; i++) {
                if (!argumentTypes[i].equals(declaredArgumentTypes[i])) {
                    continue;
                }
            }

            // hey, all passed!
            return true;
        }
        return false;
    }



    @Override
    public void visitTypedInstruction(final TypedInstruction typedinstruction) {
        Type type = typedinstruction.getType(this.cpg);
        while (type instanceof ArrayType) {
            type = ((ArrayType) type).getElementType();
        }

        if (type instanceof ObjectType) {
            SignatureUtil.checkSignatureDependencies(context.getRepository(), context.getResult(), type.getSignature());
        }
    }

}
