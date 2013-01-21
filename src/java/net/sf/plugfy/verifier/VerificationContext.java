package net.sf.plugfy.verifier;

import org.apache.bcel.util.Repository;


/**
 * context of a verification run
 *
 * @author hendrik
 */
public class VerificationContext {

    private final VerificationResult result = new VerificationResult();

    private Repository repository;

    private ClassLoader classLoader;

    /**
     * VerificationContext
     *
     * @param repository bcel repository
     * @param classLoader class loader
     */
    public VerificationContext(Repository repository, ClassLoader classLoader) {
        this.repository = repository;
        this.classLoader = classLoader;
    }

    /**
     * @return the result
     */
    public VerificationResult getResult() {
        return result;
    }

    /**
     * @return the repository
     */
    public Repository getRepository() {
        return repository;
    }

    /**
     * @param repository Repository
     */
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    /**
     * @return recursive?
     */
    public boolean isRecursive() {
        return false;
    }

    /**
     * @return the classLoader
     */
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * @param classLoader the classLoader to set
     */
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public String toString() {
        return "VerificationContext [result=" + result + "]";
    }

}
