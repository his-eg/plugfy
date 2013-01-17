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
