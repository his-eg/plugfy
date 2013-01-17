package net.sf.plugfy.sample;

/**
 * a sample invoked class
 *
 * @author hendrik
 * @param <T> parameter
 */
public interface SampleInvokedInterface<T> {

    /**
     * Test method
     *
     * @param param param
     * @return return
     */
    public SampleUnusedReturn<String> instanceMethod(SampleInvokedMethodParameter<SampleInvokedMethodParameterType> param);
}
