package net.sf.plugfy.verifier;

import org.apache.bcel.util.Repository;


/**
 * context of a verification run
 *
 * @author hendrik
 */
public class VerificationContext {

    private final VerificationResult result = new VerificationResult();

    private final Repository repository;

    /**
     * VerificationContext
     *
     * @param repository bcel repository
     */
    public VerificationContext(Repository repository) {
        this.repository = repository;
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

    @Override
    public String toString() {
        return "VerificationContext [result=" + result + "]";
    }

}
