/* Copyright (c) 2013-2016 HIS eG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may use this file in compliance with the Apache License, Version 2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sf.plugfy.verifier.webflow;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import net.sf.plugfy.verifier.VerificationContext;

import org.apache.bcel.util.ClassLoaderRepository;
import org.junit.Test;


/**
 * tests for webflow verification
 *
 * @author markus
 */
public class WebflowVerifierTest {
    
    /**
     * Test the verify method
     * @throws Exception
     */
    @Test
    public void testVerify() throws Exception {
        final URL url = new File("sample/sample-spring-webflow.jar").toURI().toURL();
        final ClassLoader classLoader = new URLClassLoader(new URL[] {url});
        VerificationContext context = new VerificationContext(new ClassLoaderRepository(classLoader), classLoader, url);
        new WebflowVerifier().verify("booking-flow.xml", context);
        System.out.println(context);
        assertThat(context.toString(), is("VerificationContext [verified=" + url.toString() + ", result=[], missingBeanIds=[SpringViolation [sourceFile=booking-flow.xml, beanId=bookingService]]]"));
    }

}
