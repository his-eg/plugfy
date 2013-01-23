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
 * utility methods
 *
 * @author hendrik
 */
public class Util {

    private Util() {
        // hide constructor
    }

    /**
     * finds the first element in an array which meets a condition
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
