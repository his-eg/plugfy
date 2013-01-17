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
     *
     * @param classLoader classLoader
     * @param name filename
     * @throws IOException in case of an input/output error
     */
    public void verify(ClassLoader classLoader, String name) throws IOException;

}
