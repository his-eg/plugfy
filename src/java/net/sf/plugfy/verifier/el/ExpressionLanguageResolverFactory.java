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
import java.util.HashSet;
import java.util.TreeSet;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.violations.SpringViolation;

/**
 * @author markus
 */
public class ExpressionLanguageResolverFactory {

    private final Collection<ExpressionVerifier> parsers = new HashSet<ExpressionVerifier>();

    private final VerificationContext context;

    /**
     * @param sourceFile
     * @param context
     */
    public ExpressionLanguageResolverFactory(final String sourceFile, final VerificationContext context) {
        this.context = context;
        this.parsers.add(new MethodExpressionVerifier(sourceFile, context));
    }

    /**
     * parse the expression
     * @param expression the expression to parse
     */
    public void parse(final String expression) {
        final Collection<SpringViolation> collected = new TreeSet<SpringViolation>();
        for (final ExpressionVerifier v : this.parsers) {
            if (v.matches(expression)) {
                collected.addAll(v.verify(expression));
            }
        }
        for (final SpringViolation springViolation : collected) {
            this.context.requireBean(springViolation);
        }
    }

}
