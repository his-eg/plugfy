package net.sf.plugfy.verifier;

import java.util.LinkedList;
import java.util.List;

/**
 * results of a verification run
 *
 * @author hendrik
 */
public class VerificationResult {

    private final List<String> results = new LinkedList<String>();

    /**
     * adds a result
     *
     * @param result result to add
     */
    public void add(String result) {
        results.add(result);
    }

    @Override
    public String toString() {
        return results.toString();
    }

}
