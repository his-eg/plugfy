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
package net.sf.plugfy.verifier.violations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author keunecke
 */
public class JavaViolationTest {

    /**
     * Test method for {@link net.sf.plugfy.verifier.violations.JavaViolation#equals(java.lang.Object)}
     * and {@link net.sf.plugfy.verifier.violations.JavaViolation#hashCode()}
     */
    @Test
    public void testEqualsObjectAndHashCode() {
        final JavaViolation v1 = JavaViolation.create("s1", "v1", "");
        assertThat(Boolean.valueOf(v1.equals(JavaViolation.create("s1", "v1", ""))), is(Boolean.TRUE));
        assertThat(Boolean.valueOf(v1.equals(JavaViolation.create("s2", "v2", ""))), is(Boolean.FALSE));
        assertThat(Integer.valueOf(v1.hashCode()), is(Integer.valueOf(JavaViolation.create("s1", "v1", "").hashCode())));
        assertThat(Boolean.valueOf(v1.equals(JavaViolation.create("s1", "v1", "method"))), is(Boolean.FALSE));
        assertThat(Boolean.valueOf(v1.equals("")), is(Boolean.FALSE));
        assertThat(Boolean.valueOf(v1.equals(null)), is(Boolean.FALSE));
        assertThat(Integer.valueOf(v1.hashCode()), not(is(Integer.valueOf(JavaViolation.create("s1", "v1", "method").hashCode()))));
        assertThat(Boolean.valueOf(v1.equals(JavaViolation.create("s1", "v1", null))), is(Boolean.FALSE));
        final JavaViolation v2 = JavaViolation.create("s2", "v2", null);
        assertThat(Boolean.valueOf(v2.equals(JavaViolation.create("s2", "v2", null))), is(Boolean.TRUE));
        assertThat(Boolean.valueOf(v2.equals(JavaViolation.create("s2", "v2", ""))), is(Boolean.FALSE));
        assertThat(Boolean.valueOf(v2.equals(JavaViolation.create("s2", "v2", "method"))), is(Boolean.FALSE));
    }

    /**
     * Test method for {@link net.sf.plugfy.verifier.violations.JavaViolation#toString()}.
     */
    @Test
    public void testToString() {
        final JavaViolation v1 = JavaViolation.create("s1", "v1", "method");
        assertThat(v1.toString(), is("JavaViolation [sourceType=s1, requiredType=v1, requiredMethod=method]"));
        final JavaViolation v2 = JavaViolation.create("s2", "v2", null);
        assertThat(v2.toString(), is("JavaViolation [sourceType=s2, requiredType=v2]"));
    }

    /**
     * Test method for {@link net.sf.plugfy.verifier.violations.JavaViolation#compareTo(net.sf.plugfy.verifier.violations.AbstractViolation)}.
     */
    @Test
    public void testCompareTo() {
        final JavaViolation v1 = JavaViolation.create("s1", "v1", "method");
        final JavaViolation v2 = JavaViolation.create("s1", "v1", "nethdo");
        final JavaViolation v3 = JavaViolation.create("s1", "v1", null);
        final JavaViolation v4 = JavaViolation.create("s2", "v2", "method");
        assertThat(Integer.valueOf(v1.compareTo(v2)), is(Integer.valueOf(-1)));
        assertThat(Integer.valueOf(v2.compareTo(v1)), is(Integer.valueOf(1)));
        assertThat(Integer.valueOf(v1.compareTo(v1)), is(Integer.valueOf(0)));
        assertThat(Integer.valueOf(v1.compareTo(v3)), is(Integer.valueOf(1)));
        assertThat(Integer.valueOf(v1.compareTo(v4)), is(Integer.valueOf(-1)));
        assertThat(Integer.valueOf(v1.compareTo(SpringViolation.create("","",""))), not(is(Integer.valueOf(0))));
    }

    /**
     * Test method for {@link net.sf.plugfy.verifier.violations.JavaViolation#create(String, String, String)}
     */
    @Test
    public void testCreate() {
        final JavaViolation actual = JavaViolation.create("source", "class", "method");
        assertThat(actual, is(JavaViolation.create("source", "class", "method")));
    }

    /**
     * Test method for {@link net.sf.plugfy.verifier.violations.JavaViolation#create(String, String, String)}
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithExceptionSourceType() {
        JavaViolation.create(null, null, null);
    }

    /**
     * Test method for {@link net.sf.plugfy.verifier.violations.JavaViolation#create(String, String, String)}
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithExceptionRequiredType() {
        JavaViolation.create("s", null, null);
    }

}
