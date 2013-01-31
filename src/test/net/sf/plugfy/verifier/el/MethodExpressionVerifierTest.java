/*
 * Copyright (c) 2013 HIS GmbH All Rights Reserved.
 *
 * $Id$
 *
 * $Log$
 *
 * Created on 30.01.2013 by keunecke
 */
package net.sf.plugfy.verifier.el;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author keunecke
 * @version $Revision$
 */
public class MethodExpressionVerifierTest {

    private final ExpressionVerifier verifier = new MethodExpressionVerifier();

    /**
     * Test method for {@link net.sf.plugfy.verifier.el.MethodExpressionVerifier#matches(java.lang.String)}.
     */
    @Test
    public void testMatches() {
        final String expressionMethod1 = "someControllerBean.aNiceMethod()";
        assertThat(Boolean.valueOf(this.verifier.matches(expressionMethod1)), is(Boolean.TRUE));
        final String expressionMethod2 = "someControllerBean.anotherBean.aNiceMethod()";
        assertThat(Boolean.valueOf(this.verifier.matches(expressionMethod2)), is(Boolean.FALSE));
    }

    /**
     * Test method for {@link net.sf.plugfy.verifier.el.MethodExpressionVerifier#verify(String)}.
     */
    @Test
    public void testVerify() {
        final String expressionMethod1 = "someControllerBean.aNiceMethod()";
        this.verifier.verify(expressionMethod1);
        final String expressionMethod2 = "someControllerBean.anotherBean.aNiceMethod()";
        this.verifier.verify(expressionMethod2);
    }

}
