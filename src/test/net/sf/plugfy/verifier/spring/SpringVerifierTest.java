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
package net.sf.plugfy.verifier.spring;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.container.JarVerifier;

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
    public void testVerifierClasses() throws Exception {
        final URL url = new File("sample/sample-spring.jar").toURI().toURL();
        final ClassLoader classLoader = new URLClassLoader(new URL[] {url});
        final VerificationContext context = new VerificationContext(new ClassLoaderRepository(classLoader), classLoader, url);
        new SpringVerifier().verify("bean-config-spring.xml", context);
        System.out.println(context.getResult());
        System.out.println("-------------------");
        assertThat(context.getResult().toString(), is("[SpringViolation [sourceFile=bean-config-spring.xml, beanId=missingBean, beanClass=org.springframework.samples.jpetstore.dao.ibatis.SqlMapAccountDao]]"));
    }

    /**
     * Test if missing bean ids are found
     * 
     * @throws Exception
     */
    @Test
    public void testMissingBeanIdsWithExistingBeanContext() throws Exception {
        final URL url = new File("sample/sample-spring.jar").toURI().toURL();
        final ClassLoader classLoader = new URLClassLoader(new URL[] { url });
        final Map<String, String> beans = new HashMap<String, String>();
        beans.put("sampleBeanPreconfigured", "net.sf.plugfy.sample.SampleClass");
        final VerificationContext context = new VerificationContext(new ClassLoaderRepository(classLoader), classLoader, url, beans);
        new JarVerifier().verify(url, context);
        System.out.println(context);
        System.out.println("-------------------");
        assertThat(context.toString(),
                        is("VerificationContext [verified="
                                        + url.toString()
                                        + ", result=[SpringViolation [sourceFile=bean-config-spring.xml, beanId=missingBean, beanClass=org.springframework.samples.jpetstore.dao.ibatis.SqlMapAccountDao]], missingBeanIds=[SpringViolation [sourceFile=bean-config-spring.xml, beanId=sampleBeanMissing], SpringViolation [sourceFile=bean-config-spring.xml, beanId=missingFactory], SpringViolation [sourceFile=bean-config-spring.xml, beanId=sampleBeanMissingParent]]]"));
    }

}
