package net.sf.plugfy.verifier.java;

import java.io.IOException;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.Verifier;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;

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
        SignatureUtil.checkSignatureDependencies(context.getRepository(), context.getResult(), javaClass);
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
        SignatureUtil.checkSignatureDependencies(context.getRepository(), context.getResult(), field);
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
        SignatureUtil.checkSignatureDependencies(context.getRepository(), context.getResult(), method);
    }

}
