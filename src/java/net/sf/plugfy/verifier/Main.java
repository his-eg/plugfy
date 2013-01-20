package net.sf.plugfy.verifier;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.sf.plugfy.verifier.container.JarVerifier;

/**
 * verifies the dependencies
 *
 * @author hendrik
 */
public class Main {

    /**
     * entry point
     *
     * @param args jar file
     * @throws IOException in case of an input/output error
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Please provide the name of the .jar file to analyse as parameter.");
            System.exit(1);
        }

        File file = new File(args[0]);
        if (file.isDirectory()) {
            // TODO
        } else {
            URL url = file.toURI().toURL();
            VerificationContext context = new VerificationContext(null);
            new JarVerifier().verify(url, context);
            System.out.println(context);
        }
    }
}
