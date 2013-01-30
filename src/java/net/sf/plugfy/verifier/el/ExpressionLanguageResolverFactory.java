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

/**
 * @author markus
 * @version $Revision$
 */
public class ExpressionLanguageResolverFactory {

    private Collection<ExpressionVerifier> parsers;

    /**
     * parse the expression
     */
    public void parse(final String expression) {
        for (final ExpressionVerifier v : this.parsers) {
            if (v.matches(expression)) {
                v.verify(expression);
            }
        }
    }

}
