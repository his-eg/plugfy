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

import java.io.IOException;

/**
 * dummy verifier will not complain about anything
 *
 * @author hendrik
 */
public class DummyVerifier implements Verifier {

    /**
     * verifies the resource
     * @param name filename
     * @param context verification context
     *
     * @throws IOException in case of an input/output error
     */
    @Override
    public void verify(String name, VerificationContext context) throws IOException {
        // everything is fine
    }

}
