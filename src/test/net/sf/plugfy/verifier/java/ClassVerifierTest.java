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
                        equalTo("[JavaViolation [missingType=net.sf.plugfy.sample.SampleArrayField], JavaViolation [missingType=net.sf.plugfy.sample.SampleField], JavaViolation [missingType=net.sf.plugfy.sample.SampleFieldInstance], JavaViolation [missingType=net.sf.plugfy.sample.SampleFieldParameter], JavaViolation [missingType=net.sf.plugfy.sample.SampleIface], JavaViolation [missingType=net.sf.plugfy.sample.SampleIfaceTypeParameter], JavaViolation [missingType=net.sf.plugfy.sample.SampleInvoked], JavaViolation [missingType=net.sf.plugfy.sample.SampleInvokedInterface], JavaViolation [missingType=net.sf.plugfy.sample.SampleInvokedStatic], JavaViolation [missingType=net.sf.plugfy.sample.SampleLocalVariable], JavaViolation [missingType=net.sf.plugfy.sample.SampleLocalVariableInstance], JavaViolation [missingType=net.sf.plugfy.sample.SampleMethodParameter], JavaViolation [missingType=net.sf.plugfy.sample.SampleMethodParameterType], JavaViolation [missingType=net.sf.plugfy.sample.SampleMethodReturnType], JavaViolation [missingType=net.sf.plugfy.sample.SampleParent], JavaViolation [missingType=net.sf.plugfy.sample.SampleParentClassTypeParameter], JavaViolation [missingType=net.sf.plugfy.sample.SampleReturn], JavaViolation [missingType=net.sf.plugfy.sample.SampleStaticBlockFieldInstance], JavaViolation [missingType=net.sf.plugfy.sample.SampleStaticField], JavaViolation [missingType=net.sf.plugfy.sample.SampleStaticFieldInstance], JavaViolation [missingType=net.sf.plugfy.sample.SampleStaticFieldParameter], JavaViolation [missingType=net.sf.plugfy.sample.SampleUnusedReturn]]"));
        // offen SampleClassTypeParameter, SampleLocalVariableType, SampleInvokedMethodParameter, SampleInvokedMethodParameterType, SampleInvokedParent

        context = new VerificationContext(new ClassLoaderRepository(classLoader), classLoader, url);
        new ClassVerifier().verify("net.sf.plugfy.sample.SampleClass$SampleInner", context);

        System.out.println(context.getResult());
        assertThat(context.getResult().toString(), equalTo("[JavaViolation [missingType=net.sf.plugfy.sample.SampleInnerParameter], JavaViolation [missingType=net.sf.plugfy.sample.SampleInnerParent]]"));
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

}
