/* Copyright (c) 2013-2016 HIS eG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may use this file in compliance with the Apache License, Version 2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.plugfy.verifier.el;

import java.util.Collection;

import net.sf.plugfy.verifier.violations.SpringViolation;

/**
 * @author markus
 */
public interface ExpressionVerifier {

    /**
     * Does the given Expression fit to this verifier
     * 
     * @param expression
     * @return true if the expression can be handled by this verifier
     */
    public boolean matches(final String expression);

    /**
     * Verify the given expression
     * 
     * @param expression
     * @return collection of possible spring violations
     */
    public Collection<SpringViolation> verify(String expression);

}
