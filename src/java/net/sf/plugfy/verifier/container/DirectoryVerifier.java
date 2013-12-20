package net.sf.plugfy.verifier.container;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.Verifier;
import net.sf.plugfy.verifier.VerifierFactory;

/**
 * Verifier for directories
 * 
 * Traverses a directory structure and verifies the content
 *  
 * @author markus
 */
public class DirectoryVerifier implements Verifier {

    @Override
    public void verify(String name, VerificationContext context) throws IOException {
        File dir = new File(name);
        if (dir.exists() && dir.isDirectory()) {
            List<File> contents = Arrays.asList(dir.listFiles());
            for (File file : contents) {
                new VerifierFactory().create(file.getAbsolutePath()).verify(file.getAbsolutePath(), context);
            }
        } else {
            throw new IllegalArgumentException("You can only verify existing directories. Not existing or no directory: " + name);
        }
    }

}
