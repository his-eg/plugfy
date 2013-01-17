package net.sf.plugfy.sample;

/**
 * a sample invoked class
 *
 * @author hendrik
 * @param <T> parameter
 */
public class SampleInvoked<T> implements SampleInvokedInterface<T> {
    // empty

    /**
     * Test method
     *
     * @param param param
     * @return return
     */
    @Override
    public SampleUnusedReturn<String> instanceMethod(SampleInvokedMethodParameter<SampleInvokedMethodParameterType> param) {
        System.out.println("instanceMethod: " + param);
        return new SampleUnusedReturn<String>();
    }
}
