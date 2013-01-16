package net.sf.plugfy.verifier;

import java.io.IOException;
import java.net.URL;

/**
 * dummy verifier will not complain about anything
 *
 * @author hendrik
 */
public class DummyVerifier implements Verifier {

    @Override
    public void verify(URL url) throws IOException {
        // everything is fine
    }

}
