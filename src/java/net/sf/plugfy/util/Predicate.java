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
