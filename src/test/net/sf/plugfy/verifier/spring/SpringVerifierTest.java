package net.sf.plugfy.verifier.spring;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import net.sf.plugfy.verifier.VerificationContext;

import org.apache.bcel.util.ClassLoaderRepository;
import org.junit.Test;

/**
 *  Unit tests for the spring verifier
 * 
 * @author markus
 */
public class SpringVerifierTest {
    
    /**
     * Test if missing bean classes are found
     * 
     * @throws Exception
     */
    @Test
    public void testVerifier() throws Exception {
        final URL url = new File("sample/sample-spring.jar").toURI().toURL();
        final ClassLoader classLoader = new URLClassLoader(new URL[] {url});
        VerificationContext context = new VerificationContext(new ClassLoaderRepository(classLoader), classLoader, url);
        new SpringVerifier().verify("bean-config.xml", context);
        System.out.println(context.getResult());
        System.out.println("-------------------");
        assertThat(context.getResult().toString(), is("[JavaViolation [sourceType=bean-config.xml, requiredType=org.springframework.samples.jpetstore.dao.ibatis.SqlMapAccountDao]]"));
    }

}
