package net.sf.plugfy.verifier.java;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import net.sf.plugfy.verifier.VerificationContext;

import org.apache.bcel.util.ClassLoaderRepository;
import org.junit.Test;

/**
 * tests for ClassVerifier
 *
 * @author brummermann
 */
public class ClassVerifierTest {

    /**
     * test
     *
     * @throws IOException in case of an input/output error
     */
    @Test
    public void test() throws IOException {
        URL url = new File("sample/sample.jar").toURI().toURL();
        ClassLoader classLoader = new URLClassLoader(new URL[] {url});
        VerificationContext context = new VerificationContext(new ClassLoaderRepository(classLoader));
        new ClassVerifier().verify(classLoader, "net.sf.plugfy.sample.SampleClass", context);
        System.out.println(context.getResult());

        System.out.println("-------------------");

        context = new VerificationContext(new ClassLoaderRepository(classLoader));
        new ClassVerifier().verify(classLoader, "net.sf.plugfy.sample.SampleClass$SampleInner", context);
        System.out.println(context.getResult());

    }

}
