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
import java.util.HashSet;

import net.sf.plugfy.verifier.VerificationContext;

/**
 * @author markus
 * @version $Revision$
 */
public class ExpressionLanguageResolverFactory {

    private final Collection<ExpressionVerifier> parsers = new HashSet<ExpressionVerifier>();

    private final String sourceFile;

    private final VerificationContext context;

    /**
     * @param sourceFile
     * @param context
     */
    public ExpressionLanguageResolverFactory(final String sourceFile, final VerificationContext context) {
        this.sourceFile = sourceFile;
        this.context = context;
        this.parsers.add(new MethodExpressionVerifier(sourceFile, context));
    }

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
