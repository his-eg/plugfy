package net.sf.plugfy.verifier;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * results of a verification run
 *
 * @author hendrik
 */
public class VerificationResult implements Iterable<String> {

    private final Set<String> results = new TreeSet<String>();

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

    @Override
    public Iterator<String> iterator() {
        return results.iterator();
    }

}
