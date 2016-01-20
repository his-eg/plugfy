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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Iterator;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.violations.SpringViolation;

import org.junit.Test;

/**
 * @author keunecke
 */
public class MethodExpressionVerifierTest {

    private final ExpressionVerifier verifier = new MethodExpressionVerifier("file-flow.xml", new VerificationContext(null, null, null));

    /**
     * Test method for {@link net.sf.plugfy.verifier.el.MethodExpressionVerifier#matches(java.lang.String)}.
     */
    @Test
    public void testMatches() {
        final String expressionMethod1 = "someControllerBean.aNiceMethod()";
        assertThat(Boolean.valueOf(this.verifier.matches(expressionMethod1)), is(Boolean.TRUE));
        final String expressionMethod2 = "someControllerBean.anotherBean.aNiceMethod()";
        assertThat(Boolean.valueOf(this.verifier.matches(expressionMethod2)), is(Boolean.FALSE));
        final String expressionMethod3 = "someControllerBean.aNiceMethod(a,b)";
        assertThat(Boolean.valueOf(this.verifier.matches(expressionMethod3)), is(Boolean.TRUE));
    }

    /**
     * Test method for {@link net.sf.plugfy.verifier.el.MethodExpressionVerifier#verify(String)}.
     */
    @Test
    public void testVerify() {
        final String expressionMethod1 = "someControllerBean.aNiceMethod()";
        final Collection<SpringViolation> verify = this.verifier.verify(expressionMethod1);
        final Iterator<SpringViolation> it = verify.iterator();
        assertThat(Boolean.valueOf(it.hasNext()), is(Boolean.TRUE));
        assertThat(it.next(), is(SpringViolation.create("file-flow.xml", "someControllerBean", null)));
    }

}
