package net.sf.plugfy.verifier.container;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.sf.plugfy.verifier.VerificationResult;
import net.sf.plugfy.verifier.Verifier;
import net.sf.plugfy.verifier.VerifierFactory;

/**
 * verifies a .jar file
 *
 * @author hendrik
 */
public class JarVerifier implements Verifier {

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
        URL url = classLoader.getResource(name);
        verify(url, result);
    }

    /**
     * verifies the resource
     *
     * @param url url to resource
     * @param result result of the verification
     * @throws IOException in case of an input/output error
     */
    public void verify(URL url, VerificationResult result) throws IOException {
        ClassLoader subClassLoader = new URLClassLoader(new URL[] {url});
        ZipInputStream zis = new ZipInputStream(url.openStream());

        try {
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                String filename = entry.getName();
                if (!filename.endsWith("/")) {
                    Verifier verifier = new VerifierFactory().create(filename);
                    verifier.verify(subClassLoader, filename, result);
                }
                entry = zis.getNextEntry();
            }
        } finally {
            zis.close();
        }
    }
}