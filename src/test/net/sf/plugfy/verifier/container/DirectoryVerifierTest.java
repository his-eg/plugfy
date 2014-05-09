/**
 * 
 */
package net.sf.plugfy.verifier.container;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.VerificationResult;

import org.apache.bcel.util.ClassLoaderRepository;
import org.apache.bcel.util.Repository;
import org.junit.Test;


/**
 * Tests for verification of directories
 * 
 * @author keunecke
 */
public class DirectoryVerifierTest {

    /**
     * Test of an extracted HIS hotfix
     * 
     * @throws Exception
     */
    @Test
    public void testVerifyWithBrokenHotfixExtracted() throws Exception {
        File file = new File("sample/hotfix/WEB-INF/classes");
        URL url = file.toURI().toURL();
        ClassLoader classLoader = new URLClassLoader(new URL[] { url });
        Repository repository = new ClassLoaderRepository(classLoader);
        VerificationContext context = new VerificationContext(repository, classLoader, url, new HashMap<String, String>(), true);
        new DirectoryVerifier().verify("sample/hotfix/", context);
        VerificationResult result = context.getResult();
        System.out.println(result);
        assertThat(result.toString(), is("[JavaViolation [sourceType=TestClassA, requiredType=TestClassB]]"));
    }

}
