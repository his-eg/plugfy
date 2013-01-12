package net.sf.plugfy.util;

/**
 * equal predicate
 *
 * @author hendrik
 * @param <T> type
 */
public class EqualPredicate<T> implements Predicate<T> {

    private final T t;

    /**
     * equal predicate
     *
     * @param t base object
     */
    public EqualPredicate(T t) {
        this.t = t;
    }

    @Override
    public boolean apply(T myT) {
        return t.equals(myT);
    }

}
