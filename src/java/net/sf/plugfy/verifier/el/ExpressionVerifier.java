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

import java.util.Collection;

import net.sf.plugfy.verifier.violations.SpringViolation;

/**
 * @author markus
 * @version $Revision$
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
