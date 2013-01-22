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
     * @param name filename
     * @param context verification context
     *
     * @throws IOException in case of an input/output error
     */
    @Override
    public void verify(String name, VerificationContext context) throws IOException {
        if (!context.isRecursive()) {
            return;
        }
        URL url = context.getClassLoader().getResource(name);
        if (url != null) {
            verify(url, context);
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
        Repository oldRepository = context.getRepository();
        ClassLoader oldClassLoader = context.getClassLoader();

        ClassLoader subClassLoader = new URLClassLoader(new URL[] {url}, oldClassLoader);
        context.setRepository(new ClassLoaderRepository(subClassLoader));
        context.setClassLoader(subClassLoader);

        ZipInputStream zis = new ZipInputStream(url.openStream());

        try {
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                String filename = entry.getName();
                if (!filename.endsWith("/")) {
                    Verifier verifier = new VerifierFactory().create(filename);
                    verifier.verify(filename, context);
                }
                entry = zis.getNextEntry();
            }
        } finally {
            zis.close();
        }
        context.setRepository(oldRepository);
        context.setClassLoader(oldClassLoader);
    }
}
