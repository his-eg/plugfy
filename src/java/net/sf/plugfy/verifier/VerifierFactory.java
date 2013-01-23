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

import java.util.Locale;

import net.sf.plugfy.verifier.container.JarVerifier;
import net.sf.plugfy.verifier.java.ClassVerifier;

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
        } else if (lower.endsWith(".class")) {
            return new ClassVerifier();
        }

        // if there is no verifier, return a dummy one instead of null
        return new DummyVerifier();
    }
}
