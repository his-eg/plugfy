package net.sf.plugfy.verifier.java;

import java.io.IOException;
import java.util.Arrays;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.Verifier;

import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Signature;
import org.apache.bcel.classfile.Utility;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.Type;

/**
 * verifies a java class
 *
 * @author hendrik
 */
public class ClassVerifier implements Verifier {
    private VerificationContext context;
    private JavaClass javaClass;
    private ConstantPoolGen cpg;

    /**
     * verifies the resource
     *
     * @param classLoader classLoader
     * @param name filename
     * @param verificationContext verification context
     * @throws IOException in case of an input/output error
     */
    @Override
    public void verify(ClassLoader classLoader, String name, VerificationContext verificationContext) throws IOException {
        this.context = verificationContext;
        try {
            javaClass = context.getRepository().loadClass(name.replace('/', '.').replaceAll("\\.class$", ""));
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
        cpg = new ConstantPoolGen(javaClass.getConstantPool());
//        DescendingVisitor descendingVisitor = new DescendingVisitor(javaClass, new ClassVisitor(context));
//        descendingVisitor.visit();

        analyzeClassSignature();
        analyzeFields();
        analyzeMethods();
    }

    /**
     * analyze the class signature
     */
    private void analyzeClassSignature() {
        // TODO Auto-generated method stub
    }

    /**
     * analyze fields
     */
    private void analyzeFields() {
        for (Field field : javaClass.getFields()) {
            analyzeField(field);
        }
    }

    /**
     * analyze field
     *
     * @param field field
     */
    private void analyzeField(Field field) {
        System.out.println("Field: " + field.getSignature() + "_/// " + Type.getType(field.getSignature()));
    }

    /**
     * analyze methods
     */
    private void analyzeMethods() {
        for (Method method : javaClass.getMethods()) {
            analyzeMethod(method);
        }
    }

    /**
     * analyze method
     *
     * @param method method
     */
    private void analyzeMethod(Method method) {
        System.out.println("Method: " + method.getName());
        System.out.println("   Sig: " + method);
        System.out.println("   S2:  " + Utility.methodSignatureToString(method.getSignature(), method.getName(),  Utility.accessToString(method.getAccessFlags()), true, method.getLocalVariableTable()));
        System.out.println("      " + method.getSignature());
        System.out.println("   <- " + method.getReturnType());
        System.out.println("      " + Arrays.asList(method.getArgumentTypes()));

        for (Attribute attribute : method.getAttributes()) {
            if (attribute instanceof Signature) {
                Signature sig = (Signature) attribute;
                System.out.println("         A: " + sig.getSignature());
                System.out.println("_____" + Type.getReturnType(sig.getSignature()));
                // System.out.println("_____" + Type.getArgumentTypes(sig.getSignature()));
            }
        }

        for (Type type : method.getArgumentTypes()) {
            System.out.println("              type: " + type + " //// " + type.getSignature());
        }
    }

}
