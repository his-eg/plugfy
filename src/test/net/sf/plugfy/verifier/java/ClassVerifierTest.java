package net.sf.plugfy.verifier.java;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import net.sf.plugfy.verifier.VerificationResult;

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
        VerificationResult result = new VerificationResult();
        URL url = new File("sample/sample.jar").toURI().toURL();
        ClassLoader classLoader = new URLClassLoader(new URL[] {url});
        new ClassVerifier().verify(classLoader, "net.sf.plugfy.sample.SampleClass", result);
        System.out.println(result);

    }

}
