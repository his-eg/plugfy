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
     * @throws IOException in case of an input/output error
     */
    @Override
    public void verify(ClassLoader classLoader, String name) throws IOException {
        // everything is fine
    }

}
