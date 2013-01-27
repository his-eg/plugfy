/**
 * 
 */
package net.sf.plugfy.verifier.violations;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;


/**
 * 
 * @author markus
 */
public class SpringViolationTest {
    
    @Test
    public void testEqualsAndHashCode() throws Exception {
        SpringViolation v1 = SpringViolation.create("f1", "b1", "bc1");
        SpringViolation v2 = SpringViolation.create("f1", "b1", "bc1");
        assertThat(v1, is(v1));
        assertThat(v1, is(v2));
        assertThat(v2, is(v1));
    }

}
