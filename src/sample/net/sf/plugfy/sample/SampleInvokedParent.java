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
