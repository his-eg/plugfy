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
 * Violation in Spring configurations
 * 
 * @author markus
 */
public class SpringViolation extends AbstractViolation {
    
    private final String sourceFile;
    
    private final String beanId;
    
    private final String beanClass;
    
    /**
     * factory method
     * 
     * @param sourceFile 
     * @param beanId 
     * @param beanClass 
     * 
     * @return a new SpringViolation
     */
    public static SpringViolation create(String sourceFile, String beanId, String beanClass) {
        if(sourceFile == null) {
           throw new IllegalArgumentException("A SpringViolation must have a sourceFile"); 
        }
        if(beanId == null) {
            throw new IllegalArgumentException("A SpringViolation must have a beanId"); 
         }
        return new SpringViolation(sourceFile, beanId, beanClass);
    }

    

    /**
     * @param sourceFile
     * @param beanId
     * @param beanClass
     */
    private SpringViolation(String sourceFile, String beanId, String beanClass) {
        this.sourceFile = sourceFile;
        this.beanId = beanId;
        this.beanClass = beanClass;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((beanClass == null) ? 0 : beanClass.hashCode());
        result = prime * result + ((beanId == null) ? 0 : beanId.hashCode());
        result = prime * result + ((sourceFile == null) ? 0 : sourceFile.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SpringViolation other = (SpringViolation) obj;
        if (beanClass == null) {
            if (other.beanClass != null) {
                return false;
            }
        } else if (!beanClass.equals(other.beanClass)) {
            return false;
        }
        if (!beanId.equals(other.beanId)) {
            return false;
        }
        return sourceFile.equals(other.sourceFile);
    }



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SpringViolation [");
        builder.append("sourceFile=");
        builder.append(sourceFile);
        builder.append(", ");
        builder.append("beanId=");
        builder.append(beanId);
        if (beanClass != null) {
            builder.append(", ");
            builder.append("beanClass=");
            builder.append(beanClass);
        }
        builder.append("]");
        return builder.toString();
    }



    @Override
    public int compareTo(AbstractViolation other) {
        if (other == null) {
            throw new NullPointerException();
        }
        if(!(other instanceof SpringViolation)) {
            return this.getClass().getSimpleName().compareTo(other.getClass().getSimpleName());
        }
        
        SpringViolation o = (SpringViolation) other;
        
        final String oSourceFile = o.sourceFile;
        final String oBeanId = o.beanId;
        final String oBeanClass = o.beanClass;
        
        //source file
        final int tempSourceFile = this.sourceFile.compareTo(oSourceFile);
        if (tempSourceFile != 0) {
            return tempSourceFile;
        }
        
        //required type
        final int tempBeanId = this.beanId.compareTo(oBeanId);
        if (tempBeanId != 0) {
            return tempBeanId;
        }
        
        //required method
        if (this.beanClass == null) {
            if (oBeanClass != null) {
                return -1;
            } else {
                return 0;
            }
        }
        
        if (oBeanClass != null) {
            return this.beanClass.compareTo(oBeanClass);
        }
        
        return 1;
    }



    /**
     * @return the beanId
     */
    public String getBeanId() {
        return beanId;
    }
    
}
