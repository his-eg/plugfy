package net.sf.plugfy.verifier;

import java.io.IOException;

/**
 * dummy verifier will not complain about anything
 *
 * @author hendrik
 */
public class DummyVerifier implements Verifier {

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
        // everything is fine
    }

}
