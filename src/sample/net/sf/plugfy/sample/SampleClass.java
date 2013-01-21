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
        SampleInvokedInterface<SampleLocalVariableType> i = new SampleInvoked<SampleLocalVariableType>();
        i.instanceMethod(null);

        return null;
    }

    public class SampleInner extends SampleInnerParent<SampleInnerParameter> {
        // empty
    }
}