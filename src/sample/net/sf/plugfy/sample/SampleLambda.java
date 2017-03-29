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
package net.sf.plugfy.sample;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * Some lambda expressions.
 * 
 * The particular case with lambda expressions is that BCEL always finds "java.lang.Object" as targetClass,
 * the functional interface as returnType, and the functional method as method.
 *
 * @author tneumann
 */
public class SampleLambda {
    private List<String> testList = Arrays.asList("aaa", "bbb", "ccccc", "dddd", "ee", "ffff");

    /**
     * Test a lambda expression that produces a Predicate used for filtering.
     * The functional method of Predicate is test(Object).
     */
    @Test
    public void testPredicate() {
        List<String> filteredList = testList.stream().filter(str -> str.length()>3).collect(Collectors.toList());
        System.out.println("filteredList = " + filteredList);
    }
    
    /**
     * Test a lambda expression that produces a Consumer, which prints all list elements with more than 3 characters.
     * The functional method of Consumer is accept(Object).
     */
    @Test
    public void testConsumer() {
        testList.stream().forEach(str -> { if (str.length()>3) System.out.println(str); } );
    }
    
    /**
     * Test a lambda expression that produces a BinaryOperator to choose the first list element with maximum length.
     * The functional method of BinaryOperator is apply(Object, Object).
     */
    @Test
    public void testBinaryOperator() {
        String result = testList.stream().reduce((previousChoice, next) -> next.length() > previousChoice.length() ? next : previousChoice ).get();
        System.out.println("result = " + result);
    }
}
