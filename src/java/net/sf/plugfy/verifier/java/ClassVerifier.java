package net.sf.plugfy.verifier.java;

import java.io.IOException;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.Verifier;

import org.apache.bcel.classfile.DescendingVisitor;
import org.apache.bcel.classfile.JavaClass;

/**
 * verifies a java class
 *
 * @author brummermann
 */
public class ClassVerifier implements Verifier {

    /**
     * verifies the resource
     *
     * @param classLoader classLoader
     * @param name filename
     * @param context verification context
     * @throws IOException in case of an input/output error
     */
    @Override
    public void verify(ClassLoader classLoader, String name, VerificationContext context) throws IOException {
        JavaClass javaClass = null;
        try {
            javaClass = context.getRepository().loadClass(name.replace('/', '.').replaceAll("\\.class$", ""));
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
        DescendingVisitor descendingVisitor = new DescendingVisitor(javaClass, new ClassVisitor(context));
        descendingVisitor.visit();
    }

}
