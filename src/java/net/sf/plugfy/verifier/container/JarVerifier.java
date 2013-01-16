package net.sf.plugfy.verifier.container;

import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * verifies a .jar file
 *
 * @author hendrik
 */
public class JarVerifier {

    public void verify(URL url) throws IOException {
        ZipInputStream zis = new ZipInputStream(url.openStream());

        try {
            ZipEntry nextEntry = zis.getNextEntry();
            while (nextEntry != null) {
                System.out.println(nextEntry);
                nextEntry = zis.getNextEntry();
            }
        } finally {
            zis.close();
        }
    }
}
