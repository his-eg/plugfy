package net.sf.plugfy.sample;

/**
 * a sample class
 *
 * @author hendrik
 * @param <T>
 */
public class SampleClass<T extends SampleClassTypeParameter> extends SampleParent<SampleParentClassTypeParameter> implements SampleIface {

    private final static SampleStaticField staticField = new SampleStaticFieldInstance();
    private final SampleField field = new SampleFieldInstance();
    private static SampleField field2;

    static {
        field2 = new SampleStaticBlockFieldInstance();
    }


    /**
     * sample method
     *
     * @param param1 a parameter
     * @return a return value
     */
    public SampleReturn<SampleMethodReturnType> method(SampleMethodParameter<SampleMethodParameterType> param1) {

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
}
