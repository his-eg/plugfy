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

/**
 * a sample invoked class
 *
 * @author hendrik
 * @param <T> parameter
 */
public class SampleInvokedStatic<T> {
    /**
     * Test method
     *
     * @param param param
     * @return return
     */
    public static SampleUnusedReturn<String> staticMethod(SampleInvokedMethodParameter<SampleInvokedMethodParameterType> param) {
        System.out.println("staticMethod: " + param);
        return new SampleUnusedReturn<String>();
    }
}
