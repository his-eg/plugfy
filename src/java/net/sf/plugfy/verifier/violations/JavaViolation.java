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

/**
 * Violation collected by the verifier represent missing  java dependencies
 * 
 * @author keunecke
 * @version $Revision$
 */
public class JavaViolation implements Comparable<JavaViolation> {

    /** name of a class missing for this violation */
    private final String missingTypeName;

    /** name of a missing method */
    private final String missingMethodName;

    /**
     * Factory method to create a new violation object
     * 
     * @param typeName
     * @param methodName
     * @return a new violation object
     */
    public static JavaViolation create(final String typeName, final String methodName) {
        return new JavaViolation(typeName, methodName);
    }

    private JavaViolation(final String typeName, final String methodName) {
        this.missingTypeName = typeName;
        this.missingMethodName = methodName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.missingMethodName == null ? 0 : this.missingMethodName.hashCode());
        result = prime * result + (this.missingTypeName == null ? 0 : this.missingTypeName.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final JavaViolation other = (JavaViolation) obj;
        if (this.missingMethodName == null) {
            if (other.missingMethodName != null) {
                return false;
            }
        } else if (!this.missingMethodName.equals(other.missingMethodName)) {
            return false;
        }
        if (this.missingTypeName == null) {
            if (other.missingTypeName != null) {
                return false;
            }
        } else if (!this.missingTypeName.equals(other.missingTypeName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("JavaViolation [");
        if (this.missingTypeName != null) {
            builder.append("missingTypeName=");
            builder.append(this.missingTypeName);
        }
        if (this.missingMethodName != null) {
            builder.append(", ");
            builder.append("missingMethodName=");
            builder.append(this.missingMethodName);
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(final JavaViolation o) {

        return 0;
    }

}
