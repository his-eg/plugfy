package net.sf.plugfy.verifier;

import java.util.Locale;

import net.sf.plugfy.verifier.container.JarVerifier;

/**
 * factory for verifiers
 *
 * @author hendrik
 */
public class VerifierFactory {

    /**
     * creates a verifier
     *
     * @param filename filename to verify
     * @return Verifier
     */
    public Verifier create(String filename) {
        String lower = filename.toLowerCase(Locale.ENGLISH);
        if (lower.endsWith(".zip") || lower.endsWith(".jar")) {
            return new JarVerifier();
        }

        // if there is no verifier, return a dummy one instead of null
        return new DummyVerifier();
    }
}
