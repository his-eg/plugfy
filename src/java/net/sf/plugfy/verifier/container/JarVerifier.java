package net.sf.plugfy.verifier.container;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.Verifier;
import net.sf.plugfy.verifier.VerifierFactory;

import org.apache.bcel.util.ClassLoaderRepository;
import org.apache.bcel.util.Repository;

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
        if (!context.isRecursive()) {
            return;
        }
        URL url = classLoader.getResource(name);
        if (url != null) {
            verify(url, classLoader, context);
        }
    }


    /**
     * verifies the resource
     *
     * @param url url to resource
     * @param context verification context
     * @throws IOException in case of an input/output error
     */
    public void verify(URL url, VerificationContext context) throws IOException {
        verify(url, this.getClass().getClassLoader(), context);
    }

    /**
     * verifies the resource
     *
     * @param url url to resource
     * @param classLoader classloader
     * @param context verification context
     * @throws IOException in case of an input/output error
     */
    public void verify(URL url, ClassLoader classLoader, VerificationContext context) throws IOException {
        ClassLoader subClassLoader = new URLClassLoader(new URL[] {url}, classLoader);
        Repository old = context.getRepository();
        context.setRepository(new ClassLoaderRepository(subClassLoader));

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
        context.setRepository(old);
    }
}
