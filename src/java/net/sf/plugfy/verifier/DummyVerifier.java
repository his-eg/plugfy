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
     * @param name filename
     * @param context verification context
     *
     * @throws IOException in case of an input/output error
     */
    @Override
    public void verify(String name, VerificationContext context) throws IOException {
        // everything is fine
    }

}
