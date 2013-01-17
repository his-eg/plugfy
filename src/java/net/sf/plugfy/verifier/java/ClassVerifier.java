package net.sf.plugfy.verifier.java;

import java.io.IOException;

import net.sf.plugfy.verifier.Verifier;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.util.ClassLoaderRepository;

/**
 * verifies a java class
 *
 * @author brummermann
 */
public class ClassVerifier implements Verifier {
    private ConstantPoolGen cpg;

    /**
     * verifies the resource
     *
     * @param classLoader classLoader
     * @param name filename
     * @throws IOException in case of an input/output error
     */
    @Override
    public void verify(ClassLoader classLoader, String name) throws IOException {
        ClassLoaderRepository repository = new ClassLoaderRepository(classLoader);
        JavaClass javaClass = null;
        try {
            javaClass = repository.loadClass(name.replace('/', '.').replaceAll("\\.class$", ""));
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
        javaClass.accept(new ClassVisitor());
        cpg = new ConstantPoolGen(javaClass.getConstantPool());

        // TODO: handle class parents and interfaces
        // TODO: handle class generic types
        // TODO: handle class fields
        // TODO: handle method parameters
        // TODO: handle method return types
        // TODO: handle method thrown exceptions

    }

}
