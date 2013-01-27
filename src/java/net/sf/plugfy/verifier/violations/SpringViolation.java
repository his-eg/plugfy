/**
 * 
 */
package net.sf.plugfy.verifier.violations;

/**
 * Violation in Spring configurations
 * 
 * @author markus
 */
public class SpringViolation {
    
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
        return new SpringViolation(sourceFile, beanId, beanClass);
    }

    /**
     * @param sourceFile
     * @param beanId
     * @param beanClass
     */
    public SpringViolation(String sourceFile, String beanId, String beanClass) {
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
        if (beanId == null) {
            if (other.beanId != null) {
                return false;
            }
        } else if (!beanId.equals(other.beanId)) {
            return false;
        }
        if (sourceFile == null) {
            if (other.sourceFile != null) {
                return false;
            }
        } else if (!sourceFile.equals(other.sourceFile)) {
            return false;
        }
        return true;
    }

}
