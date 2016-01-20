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


/**
 * Violation collected by the verifier represent missing  java dependencies
 * 
 * @author markus
 */
public class JavaViolation extends AbstractViolation {

    /** name of a required class */
    private final String requiredType;

    /** name of a required method */
    private final String requiredMethod;

    private final String sourceType;

    /**
     * Factory method to create a new violation object
     * 
     * @param sourceType
     * @param typeName
     * @param methodName
     * @return a new violation object
     */
    public static JavaViolation create(final String sourceType, final String typeName, final String methodName) {
        if (typeName == null) {
            throw new IllegalArgumentException("A JavaViolation must refer to a required type name");
        }
        if (sourceType == null) {
            throw new IllegalArgumentException("A JavaViolation must have a source type");
        }
        return new JavaViolation(sourceType, typeName, methodName);
    }

    private JavaViolation(final String sourceType, final String typeName, final String methodName) {
        this.requiredType = typeName;
        this.requiredMethod = methodName;
        this.sourceType = sourceType;
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("JavaViolation [");
        builder.append("sourceType=");
        builder.append(this.sourceType);
        builder.append(", requiredType=");
        builder.append(this.requiredType);
        if (this.requiredMethod != null) {
            builder.append(", ");
            builder.append("requiredMethod=");
            builder.append(this.requiredMethod);
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(final AbstractViolation other) {
        if (other == null) {
            throw new NullPointerException();
        }
        if(!(other instanceof JavaViolation)) {
            return this.getClass().getSimpleName().compareTo(other.getClass().getSimpleName());
        }
        
        JavaViolation o = (JavaViolation) other;
        final String oSourceType = o.sourceType;
        final String oRequiredType = o.requiredType;
        final String oRequiredMethod = o.requiredMethod;
        
        //source type
        final int tempSourceType = this.sourceType.compareTo(oSourceType);
        if (tempSourceType != 0) {
            return tempSourceType;
        }
        
        //required type
        final int tempRequiredType = this.requiredType.compareTo(oRequiredType);
        if (tempRequiredType != 0) {
            return tempRequiredType;
        }
        
        //required method
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
        result = prime * result + (this.sourceType == null ? 0 : this.sourceType.hashCode());
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
        if (this.sourceType == null) {
            if (other.sourceType != null) {
                return false;
            }
        } else if (!this.sourceType.equals(other.sourceType)) {
            return false;
        }
        return true;
    }


}
