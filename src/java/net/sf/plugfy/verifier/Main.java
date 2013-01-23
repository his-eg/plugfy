/*
 *  Copyright 2013
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may use this file in compliance with the Apache License, Version 2.0.
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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

        long start = System.currentTimeMillis();

        if (args.length != 1) {
            System.err.println("Please provide the name of the .jar file to analyse as parameter.");
            System.exit(1);
        }

        File file = new File(args[0]);
        if (file.isDirectory()) {
            // TODO
        } else {
            URL url = file.toURI().toURL();
            VerificationContext context = new VerificationContext(null, Main.class.getClassLoader());
            new JarVerifier().verify(url, context);
            System.out.println("---------------------------------");
            for (String entry : context.getResult()) {
                System.out.println(entry);
            }
        }

        System.out.println("Duration: " + (System.currentTimeMillis() - start));
    }

}