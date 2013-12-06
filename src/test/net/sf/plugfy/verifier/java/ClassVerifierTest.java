/*
 *  Copyright 2013
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may use this file in compliance with the Apache License, Version 2.0.
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.sf.plugfy.verifier.java;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.container.JarVerifier;

import org.apache.bcel.util.ClassLoaderRepository;
import org.junit.Test;

/**
 * tests for ClassVerifier
 *
 * @author brummermann
 */
public class ClassVerifierTest {

    /**
     * test
     *
     * @throws IOException in case of an input/output error
     */
    @Test
    public void test() throws IOException {
        final URL url = new File("sample/sample.jar").toURI().toURL();
        final ClassLoader classLoader = new URLClassLoader(new URL[] {url});
        VerificationContext context = new VerificationContext(new ClassLoaderRepository(classLoader), classLoader, url);
        new ClassVerifier().verify("net.sf.plugfy.sample.SampleClass", context);

        System.out.println(context.getResult());
        System.out.println("-------------------");
        assertThat(context.getResult().toString(),
                        equalTo("[JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleArrayField], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleField], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleFieldInstance], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleFieldParameter], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleIface], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleIfaceTypeParameter], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleInvoked], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleInvokedInterface], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleInvokedStatic], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleLocalVariable], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleLocalVariableInstance], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleMethodParameter], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleMethodParameterType], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleMethodReturnType], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleParent], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleParentClassTypeParameter], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleReturn], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleStaticBlockFieldInstance], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleStaticField], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleStaticFieldInstance], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleStaticFieldParameter], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass, requiredType=net.sf.plugfy.sample.SampleUnusedReturn]]"));
        // offen SampleClassTypeParameter, SampleLocalVariableType, SampleInvokedMethodParameter, SampleInvokedMethodParameterType, SampleInvokedParent

        context = new VerificationContext(new ClassLoaderRepository(classLoader), classLoader, url);
        new ClassVerifier().verify("net.sf.plugfy.sample.SampleClass$SampleInner", context);

        System.out.println(context.getResult());
        assertThat(context.getResult().toString(),
                        equalTo("[JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass$SampleInner, requiredType=net.sf.plugfy.sample.SampleInnerParameter], JavaViolation [sourceType=net.sf.plugfy.sample.SampleClass$SampleInner, requiredType=net.sf.plugfy.sample.SampleInnerParent]]"));
    }

    /**
     * test
     *
     * @throws IOException in case of an input/output error
     */
    @Test
    public void test2() throws IOException {
        final URL url = new File("sample/sample-all.jar").toURI().toURL();
        final ClassLoader classLoader = new URLClassLoader(new URL[] {url});
        final VerificationContext context = new VerificationContext(new ClassLoaderRepository(classLoader), classLoader, url);
        new ClassVerifier().verify("net.sf.plugfy.sample.SampleClass", context);
        System.out.println(context.getResult());
        assertThat(context.getResult().toString(), equalTo("[]"));
    }

    /**
     * test
     *
     * @throws IOException in case of an input/output error
     */
    @Test
    public void test3() throws IOException {
        final URL url = new File("sample/patch.zip").toURI().toURL();
        final ClassLoader classLoader = new URLClassLoader(new URL[] { url });
        final VerificationContext context = new VerificationContext(new ClassLoaderRepository(classLoader), classLoader, url);
        JarVerifier v = new JarVerifier();
        v.verify(url, context);
        System.out.println(context.getResult());
        assertThat(context.getResult().toString(), equalTo("[]"));
    }

}
