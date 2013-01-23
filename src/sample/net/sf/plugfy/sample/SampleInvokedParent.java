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
 */
public class SampleInvokedParent {
    // empty

    /**
     * Test method
     *
     * @param param param
     * @return return
     */
    public String parentMethod(int i, int[] ia, String s, String[] sa, String[][] saa, Number n, String... opt) {
        System.out.println("parentMethod");
        return "return value";
    }
}
