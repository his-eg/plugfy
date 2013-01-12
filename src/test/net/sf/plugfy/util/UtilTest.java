package net.sf.plugfy.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Test
 *
 * @author hendrik
 */
public class UtilTest {

    /**
     * tests find
     */
    @Test
    public void testFind() {
        String[] list = new String[] {"a", "b"};
        assertThat(Util.find(list, new EqualPredicate<String>("a")), equalTo("a"));
        assertThat(Util.find(list, new EqualPredicate<String>("c")), nullValue());
    }
}
