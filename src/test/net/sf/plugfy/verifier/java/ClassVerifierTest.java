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
        URL url = new File("sample/sample.jar").toURI().toURL();
        ClassLoader classLoader = new URLClassLoader(new URL[] {url});
        VerificationContext context = new VerificationContext(new ClassLoaderRepository(classLoader), classLoader);
        new ClassVerifier().verify(classLoader, "net.sf.plugfy.sample.SampleClass", context);

        System.out.println(context.getResult());
        System.out.println("-------------------");
        assertThat(context.getResult().toString(), equalTo("[net.sf.plugfy.sample.SampleArrayField, net.sf.plugfy.sample.SampleField, net.sf.plugfy.sample.SampleFieldInstance, net.sf.plugfy.sample.SampleFieldParameter, net.sf.plugfy.sample.SampleIface, net.sf.plugfy.sample.SampleIfaceTypeParameter, net.sf.plugfy.sample.SampleInvoked, net.sf.plugfy.sample.SampleInvokedInterface, net.sf.plugfy.sample.SampleInvokedStatic, net.sf.plugfy.sample.SampleLocalVariable, net.sf.plugfy.sample.SampleLocalVariableInstance, net.sf.plugfy.sample.SampleMethodParameter, net.sf.plugfy.sample.SampleMethodParameterType, net.sf.plugfy.sample.SampleMethodReturnType, net.sf.plugfy.sample.SampleParent, net.sf.plugfy.sample.SampleParentClassTypeParameter, net.sf.plugfy.sample.SampleReturn, net.sf.plugfy.sample.SampleStaticBlockFieldInstance, net.sf.plugfy.sample.SampleStaticField, net.sf.plugfy.sample.SampleStaticFieldInstance, net.sf.plugfy.sample.SampleStaticFieldParameter, net.sf.plugfy.sample.SampleUnusedReturn]"));
        // offen SampleClassTypeParameter, SampleLocalVariableType, SampleInvokedMethodParameter, SampleInvokedMethodParameterType, SampleInvokedParent

        context = new VerificationContext(new ClassLoaderRepository(classLoader), classLoader);
        new ClassVerifier().verify(classLoader, "net.sf.plugfy.sample.SampleClass$SampleInner", context);

        System.out.println(context.getResult());
        assertThat(context.getResult().toString(), equalTo("[net.sf.plugfy.sample.SampleInnerParameter, net.sf.plugfy.sample.SampleInnerParent]"));
    }

    /**
     * test
     *
     * @throws IOException in case of an input/output error
     */
    @Test
    public void test2() throws IOException {
        URL url = new File("sample/sample-all.jar").toURI().toURL();
        ClassLoader classLoader = new URLClassLoader(new URL[] {url});
        VerificationContext context = new VerificationContext(new ClassLoaderRepository(classLoader), classLoader);
        new ClassVerifier().verify(classLoader, "net.sf.plugfy.sample.SampleClass", context);
        System.out.println(context.getResult());
        assertThat(context.getResult().toString(), equalTo("[]"));
    }

}
