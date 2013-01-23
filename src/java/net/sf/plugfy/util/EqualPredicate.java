/*
 *  Copyright 2013
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may use this file in compliance with the Apache License, Version 2.0.
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
