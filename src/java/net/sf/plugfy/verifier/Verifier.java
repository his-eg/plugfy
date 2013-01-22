package net.sf.plugfy.verifier;

import java.io.IOException;

/**
 * interface for verifier
 *
 * @author hendrik
 */
public interface Verifier {

    /**
     * verifies the resource
     * @param name filename
     * @param context verification context
     *
     * @throws IOException in case of an input/output error
     */
    public void verify(String name, VerificationContext context) throws IOException;

}
