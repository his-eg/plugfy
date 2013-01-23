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

import java.util.List;
import java.util.LinkedList;

/**
 * a sample class
 *
 * @author hendrik
 * @param <T>
 */
public class SampleClass<T extends SampleClassTypeParameter> extends SampleParent<SampleParentClassTypeParameter> implements SampleIface<SampleIfaceTypeParameter> {

    private final static SampleStaticField<SampleStaticFieldParameter> staticField = new SampleStaticFieldInstance();
    private final SampleField<SampleFieldParameter> field = new SampleFieldInstance();
    private static SampleField field2;
    private final int intField = 0;
    private final int[] intArrayField = new int[0];
    private final SampleArrayField[] arrayField = new SampleArrayField[0];
    private final List<Double> list = new LinkedList<Double>();

    static {
        field2 = new SampleStaticBlockFieldInstance();
    }


    /**
     * sample method
     *
     * @param param1 a parameter
     * @return a return value
     */
    public SampleReturn<SampleMethodReturnType> method(SampleMethodParameter<SampleMethodParameterType> param1, int param2, String param3) {

        SampleLocalVariable<SampleLocalVariableType> temp = new SampleLocalVariableInstance<SampleLocalVariableType>();

        System.out.println("param1: " + param1);
        System.out.println("field: " + field);
        System.out.println("staticField: " + staticField);
        System.out.println("field2: " + field2);
        System.out.println("temp: " + temp);

        SampleInvokedStatic.staticMethod(null);
        SampleInvokedInterface<SampleLocalVariableType> si = new SampleInvoked<SampleLocalVariableType>();
        si.instanceMethod(null);

        SampleInvoked invoked = new SampleInvoked<SampleLocalVariableType>();
        
        int i = 1;
        int[] ia = new int[] {1, 2};
        String s = "s";
        String[] sa = new String[] { "sa", "sa1" };
        String[][] saa = new String[][] { { "saa", "saa1" } , { "saa2", "saa21" } };
        Double d = Double.valueOf(1);
        
        invoked.parentMethod(i , ia, s, sa, saa, d);
        invoked.parentMethod(i , ia, s, sa, saa, d, "a");
        invoked.parentMethod(i , ia, s, sa, saa, d, "a", "b");
        invoked.parentMethod(i , ia, s, sa, saa, d, sa);
        
        return null;
    }

    public class SampleInner extends SampleInnerParent<SampleInnerParameter> {
        // empty
    }
}