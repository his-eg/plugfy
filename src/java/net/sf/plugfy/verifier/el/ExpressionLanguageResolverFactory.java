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
import java.util.TreeSet;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.violations.SpringViolation;

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
     * @param expression the expression to parse
     */
    public void parse(final String expression) {
        Collection<SpringViolation> collected = new TreeSet<SpringViolation>();
        for (final ExpressionVerifier v : this.parsers) {
            if (v.matches(expression)) {
                collected.addAll(v.verify(expression));
            }
        }
        for (SpringViolation springViolation : collected) {
            this.context.requireBean(springViolation);
        }
    }

}
