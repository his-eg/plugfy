package net.sf.plugfy.verifier;

import java.io.IOException;
import java.net.URL;

/**
 * interface for verifier
 *
 * @author hendrik
 */
public interface Verifier {

    /**
     * verifies the resource
     *
     * @param url resource
     * @throws IOException in case of an input/output error
     */
    public void verify(URL url) throws IOException;

}
