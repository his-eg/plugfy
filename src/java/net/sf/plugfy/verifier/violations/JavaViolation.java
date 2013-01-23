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

    /** name of a required class */
    private final String requiredType;

    /** name of a required method */
    private final String requiredMethod;

    /**
     * Factory method to create a new violation object
     * 
     * @param typeName
     * @param methodName
     * @return a new violation object
     */
    public static JavaViolation create(final String typeName, final String methodName) {
        if (typeName == null) {
            throw new IllegalArgumentException("A JavaViolation must refer to a required type name");
        }
        return new JavaViolation(typeName, methodName);
    }

    private JavaViolation(final String typeName, final String methodName) {
        this.requiredType = typeName;
        this.requiredMethod = methodName;
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("JavaViolation [");
        if (this.requiredType != null) {
            builder.append("missingType=");
            builder.append(this.requiredType);
        }
        if (this.requiredMethod != null) {
            builder.append(", ");
            builder.append("missingMethod=");
            builder.append(this.requiredMethod);
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(final JavaViolation o) {
        if (o == null) {
            throw new NullPointerException();
        }
        final String oRequiredType = o.requiredType;
        final String oRequiredMethod = o.requiredMethod;

        //typgleichheit
        if (this.requiredType == null) {
            if (oRequiredType != null) {
                return -1;
            }
        } else {
            final int temp = this.requiredType.compareTo(oRequiredType);
            if (temp != 0) {
                return temp;
            }
        }

        //methodengleichheit
        if (this.requiredMethod == null) {
            if (oRequiredMethod != null) {
                return -1;
            } else {
                return 0;
            }
        }

        if (oRequiredMethod != null) {
            return this.requiredMethod.compareTo(oRequiredMethod);
        }

        return 1;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.requiredMethod == null ? 0 : this.requiredMethod.hashCode());
        result = prime * result + (this.requiredType == null ? 0 : this.requiredType.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final JavaViolation other = (JavaViolation) obj;
        if (this.requiredMethod == null) {
            if (other.requiredMethod != null) {
                return false;
            }
        } else if (!this.requiredMethod.equals(other.requiredMethod)) {
            return false;
        }
        if (this.requiredType == null) {
            if (other.requiredType != null) {
                return false;
            }
        } else if (!this.requiredType.equals(other.requiredType)) {
            return false;
        }
        return true;
    }

}
