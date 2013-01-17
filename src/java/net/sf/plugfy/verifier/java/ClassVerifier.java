package net.sf.plugfy.verifier.java;

import java.io.IOException;

import net.sf.plugfy.verifier.VerificationResult;
import net.sf.plugfy.verifier.Verifier;

import org.apache.bcel.classfile.DescendingVisitor;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.ClassLoaderRepository;

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
     * @param result result of the verification
     * @throws IOException in case of an input/output error
     */
    @Override
    public void verify(ClassLoader classLoader, String name, VerificationResult result) throws IOException {
        ClassLoaderRepository repository = new ClassLoaderRepository(classLoader);
        JavaClass javaClass = null;
        try {
            javaClass = repository.loadClass(name.replace('/', '.').replaceAll("\\.class$", ""));
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
        DescendingVisitor descendingVisitor = new DescendingVisitor(javaClass, new ClassVisitor(result));
        descendingVisitor.visit();
    }

}
