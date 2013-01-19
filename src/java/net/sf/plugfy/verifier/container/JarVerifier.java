package net.sf.plugfy.verifier.container;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.sf.plugfy.verifier.VerificationContext;
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
     * @param context verification context
     * @throws IOException in case of an input/output error
     */
    @Override
    public void verify(ClassLoader classLoader, String name, VerificationContext context) throws IOException {
        URL url = classLoader.getResource(name);
        verify(url, context);
    }

    /**
     * verifies the resource
     *
     * @param url url to resource
     * @param context verification context
     * @throws IOException in case of an input/output error
     */
    public void verify(URL url, VerificationContext context) throws IOException {
        ClassLoader subClassLoader = new URLClassLoader(new URL[] {url});
        ZipInputStream zis = new ZipInputStream(url.openStream());

        try {
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                String filename = entry.getName();
                if (!filename.endsWith("/")) {
                    Verifier verifier = new VerifierFactory().create(filename);
                    verifier.verify(subClassLoader, filename, context);
                }
                entry = zis.getNextEntry();
            }
        } finally {
            zis.close();
        }
    }
}
