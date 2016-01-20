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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.violations.SpringViolation;

/**
 * Verifier for method calls in expression language
 * 
 * @author keunecke
 */
public class MethodExpressionVerifier implements ExpressionVerifier {

    /**regex pattern to match simple method calls like bean.method(a,b)*/
    private final Pattern p = Pattern.compile("([a-zA-Z]*)(\\.[a-zA-Z]*)(\\(([a-zA-Z]*,)*([a-zA-Z]*)\\))");

    private final String sourceFile;

    private final VerificationContext context;

    /**
     * @param sourceFile
     * @param context
     */
    public MethodExpressionVerifier(final String sourceFile, final VerificationContext context) {
        this.context = context;
        this.sourceFile = sourceFile;
    }

    @Override
    public boolean matches(final String expression) {
        return this.p.matcher(expression).matches();
    }

    @Override
    public Collection<SpringViolation> verify(final String expression) {
        final Collection<SpringViolation> result = new ArrayList<SpringViolation>();
        final List<String> list = Arrays.asList(expression.split("\\."));
        for (final String part : list) {
            if (!part.equals(list.get(list.size() - 1))) {
                SpringViolation v = SpringViolation.create(this.sourceFile, part, null);
                result.add(v);
            }
        }
        return result;
    }

}
