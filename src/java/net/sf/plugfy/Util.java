package net.sf.plugfy;

/**
 * utility methods
 *
 * @author hendrik
 */
public class Util {

    private Util() {
        // hide constructor
    }

    /**
     * finds the first element in an array which mets a condition
     *
     * @param list list
     * @param predicate predicate
     * @return first matching element
     */
    public static <T> T find(T[] list, Predicate<T> predicate) {
        for (T t : list) {
            if (predicate.apply(t)) {
                return t;
            }
        }
        return null;
    }
}
