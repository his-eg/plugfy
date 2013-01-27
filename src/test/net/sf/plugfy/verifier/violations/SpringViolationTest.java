/**
 * 
 */
package net.sf.plugfy.verifier.violations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;


/**
 * 
 * @author markus
 */
public class SpringViolationTest {
    
    /**
     * Test equals and hashCode
     * @throws Exception
     */
    @Test
    public void testEqualsAndHashCode() throws Exception {
        SpringViolation v1 = SpringViolation.create("f1", "b1", "bc1");
        SpringViolation v2 = SpringViolation.create("f1", "b1", "bc1");
        SpringViolation v3 = SpringViolation.create("f1", "b1", null);
        SpringViolation v4 = SpringViolation.create("f4", "b1", "bc1");
        SpringViolation v5 = SpringViolation.create("f1", "b2", "bc1");
        assertThat(v1, is(v1));
        assertThat(v1, is(v2));
        assertThat(Integer.valueOf(v1.hashCode()), is(Integer.valueOf(v2.hashCode())));
        assertThat(v2, is(v1));
        assertThat(Integer.valueOf(v2.hashCode()), is(Integer.valueOf(v1.hashCode())));
        assertThat(v1, not(is(v3)));
        assertThat(v3, not(is(v1)));
        assertThat(v1, not(is(v4)));
        
        assertThat(v1, not(is(v5)));
        assertThat(v5, not(is(v1)));
        
        assertThat(Boolean.valueOf(v1.equals(null)), is(Boolean.FALSE));
        assertThat(Boolean.valueOf(v1.equals("")), is(Boolean.FALSE));
        
    }
    
    /**
     * Test toString
     * 
     * @throws Exception
     */
    @Test
    public void testToString() throws Exception {
        SpringViolation v1 = SpringViolation.create("f1", "b1", "bc1");
        assertThat(v1.toString(), is("SpringViolation [sourceFile=f1, beanId=b1, beanClass=bc1]"));
        SpringViolation v3 = SpringViolation.create("f1", "b1", null);
        assertThat(v3.toString(), is("SpringViolation [sourceFile=f1, beanId=b1]"));
    }
    
    /**
     * Test compareTo
     * 
     * @throws Exception
     */
    @Test
    public void testCompareTo() throws Exception {
        SpringViolation v1 = SpringViolation.create("f1", "b1", "bc1");
        assertThat(Integer.valueOf(v1.compareTo(v1)), is(Integer.valueOf(0)));
    }

}
