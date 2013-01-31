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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.violations.SpringViolation;

/**
 * @author keunecke
 * @version $Revision$
 */
public class MethodExpressionVerifier implements ExpressionVerifier {

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
                result.add(SpringViolation.create(this.sourceFile, part, null));
            }
        }
        return result;
    }

}
