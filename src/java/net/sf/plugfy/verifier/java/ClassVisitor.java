package net.sf.plugfy.verifier.java;

import java.util.Arrays;

import net.sf.plugfy.verifier.VerificationResult;

import org.apache.bcel.classfile.EmptyVisitor;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.InnerClass;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Signature;
import org.apache.bcel.generic.ConstantPoolGen;

/**
 * visits class files to verify dependencies
 *
 * @author hendrik
 */
public class ClassVisitor extends EmptyVisitor {
    private JavaClass javaClass;
    private ConstantPoolGen constantPoolGen;
    private final VerificationResult result;

    /**
     * ClassVisitor logs missing dependencies
     *
     * @param result result collector
     */
    public ClassVisitor(VerificationResult result) {
        this.result = result;
    }

    @Override
    public void visitJavaClass(JavaClass clazz) {
        this.javaClass = clazz;
        this.constantPoolGen = new ConstantPoolGen(javaClass.getConstantPool());
        System.out.println(this.javaClass);
        System.out.println(Arrays.toString(this.javaClass.getInterfaces()));
        System.out.println(this.javaClass.getSuperClass());
    }

    @Override
    public void visitField(Field field) {
        System.out.println("field: " + field);
    }

    @Override
    public void visitInnerClass(InnerClass innerclass) {
        System.out.println("innerclass: " + innerclass);
    }

    @Override
    public void visitLocalVariable(LocalVariable localvariable) {
        System.out.println("localvariable: " + localvariable);
    }

    @Override
    public void visitMethod(Method method) {
        System.out.println("method: " + method);
    }

    @Override
    public void visitSignature(Signature signature) {
        System.out.println("signature: " + signature);
    }

}
