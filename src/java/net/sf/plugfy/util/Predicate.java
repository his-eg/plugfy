package net.sf.plugfy.util;

/**
 * a predicate
 *
 * @author hendrik
 * @param <T> type of predicate
 */
public interface Predicate<T> {

    /**
     * applies the predicate to the data
     *
     * @param t data
     * @return true or false
     */
    public boolean apply(T t);
}
