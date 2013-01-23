/*
 * Copyright (c) 2013 HIS GmbH All Rights Reserved.
 *
 * $Id$
 *
 * $Log$
 *
 * Created on 23.01.2013 by keunecke
 */
package net.sf.plugfy.verifier.violations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author keunecke
 * @version $Revision$
 */
public class JavaViolationTest {

    /**
     * Test method for {@link net.sf.plugfy.verifier.violations.JavaViolation#equals(java.lang.Object)}
     * and {@link net.sf.plugfy.verifier.violations.JavaViolation#hashCode()}
     */
    @Test
    public void testEqualsObjectAndHashCode() {
        final JavaViolation v1 = JavaViolation.create("v1", "");
        assertThat(Boolean.valueOf(v1.equals(JavaViolation.create("v1", ""))), is(Boolean.TRUE));
        assertThat(Boolean.valueOf(v1.equals(JavaViolation.create("v2", ""))), is(Boolean.FALSE));
        assertThat(Integer.valueOf(v1.hashCode()), is(Integer.valueOf(JavaViolation.create("v1", "").hashCode())));
        assertThat(Boolean.valueOf(v1.equals(JavaViolation.create("v1", "method"))), is(Boolean.FALSE));
        assertThat(Boolean.valueOf(v1.equals("")), is(Boolean.FALSE));
        assertThat(Boolean.valueOf(v1.equals(null)), is(Boolean.FALSE));
        assertThat(Integer.valueOf(v1.hashCode()), not(is(Integer.valueOf(JavaViolation.create("v1", "method").hashCode()))));
        assertThat(Boolean.valueOf(v1.equals(JavaViolation.create("v1", null))), is(Boolean.FALSE));
        final JavaViolation v2 = JavaViolation.create("v2", null);
        assertThat(Boolean.valueOf(v2.equals(JavaViolation.create("v2", null))), is(Boolean.TRUE));
        assertThat(Boolean.valueOf(v2.equals(JavaViolation.create("v2", ""))), is(Boolean.FALSE));
        assertThat(Boolean.valueOf(v2.equals(JavaViolation.create("v2", "method"))), is(Boolean.FALSE));
    }

    /**
     * Test method for {@link net.sf.plugfy.verifier.violations.JavaViolation#toString()}.
     */
    @Test
    public void testToString() {
        final JavaViolation v1 = JavaViolation.create("v1", "method");
        assertThat(v1.toString(), is("JavaViolation [missingType=v1, missingMethod=method]"));
        final JavaViolation v2 = JavaViolation.create("v2", null);
        assertThat(v2.toString(), is("JavaViolation [missingType=v2]"));
    }

    /**
     * Test method for {@link net.sf.plugfy.verifier.violations.JavaViolation#compareTo(net.sf.plugfy.verifier.violations.JavaViolation)}.
     */
    @Test
    public void testCompareTo() {
        final JavaViolation v1 = JavaViolation.create("v1", "method");
        final JavaViolation v2 = JavaViolation.create("v1", "nethdo");
        final JavaViolation v3 = JavaViolation.create("v1", null);
        final JavaViolation v4 = JavaViolation.create("v2", "method");
        assertThat(Integer.valueOf(v1.compareTo(v2)), is(Integer.valueOf(-1)));
        assertThat(Integer.valueOf(v2.compareTo(v1)), is(Integer.valueOf(1)));
        assertThat(Integer.valueOf(v1.compareTo(v1)), is(Integer.valueOf(0)));
        assertThat(Integer.valueOf(v1.compareTo(v3)), is(Integer.valueOf(1)));
        assertThat(Integer.valueOf(v1.compareTo(v4)), is(Integer.valueOf(-1)));

    }

    /**
     * Test method for {@link net.sf.plugfy.verifier.violations.JavaViolation#create(String, String)}
     */
    @Test
    public void testCreate() {
        final JavaViolation actual = JavaViolation.create("class", "method");
        assertThat(actual, is(JavaViolation.create("class", "method")));
    }

    /**
     * Test method for {@link net.sf.plugfy.verifier.violations.JavaViolation#create(String, String)}
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithException() {
        JavaViolation.create(null, null);
    }

}
