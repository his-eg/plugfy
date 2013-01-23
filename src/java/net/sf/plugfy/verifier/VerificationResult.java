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

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import net.sf.plugfy.verifier.violations.JavaViolation;

/**
 * results of a verification run
 *
 * @author hendrik
 */
public class VerificationResult implements Iterable<JavaViolation> {

    private final Set<JavaViolation> results = new TreeSet<JavaViolation>();

    /**
     * adds a result
     *
     * @param result result to add
     */
    public void add(final JavaViolation result) {
        this.results.add(result);
    }

    @Override
    public String toString() {
        return this.results.toString();
    }

    @Override
    public Iterator<JavaViolation> iterator() {
        return this.results.iterator();
    }

}
