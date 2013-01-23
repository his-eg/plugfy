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

import java.util.Set;
import java.util.TreeSet;

import net.sf.plugfy.verifier.VerificationResult;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.ClassPath;
import org.apache.bcel.util.Repository;
import org.junit.Test;

/**
 * tests for SignatureItils
 *
 * @author hendrik
 */
public class SignatureUtilTest implements Repository {

    private static final long serialVersionUID = 6289267026355006491L;
    private final Set<String> requestedClasses = new TreeSet<String>();


    /**
     * test for checkSignatureDependencies
     */
    @Test
    public void testCheckSignatureDependencies() {
        final VerificationResult result = new VerificationResult();
        this.clear();
        SignatureUtil.checkSignatureDependencies(this, result, "Lnet/sf/plugfy/sample/SampleStaticField<Lnet/sf/plugfy/sample/SampleStaticFieldParameter;>;", "Class");
        assertThat(this.requestedClasses.toString(), equalTo("[net.sf.plugfy.sample.SampleStaticField, net.sf.plugfy.sample.SampleStaticFieldParameter]"));
        this.clear();
        SignatureUtil.checkSignatureDependencies(this, result, "I", "Class");
        assertThat(this.requestedClasses.toString(), equalTo("[]"));
        this.clear();
        SignatureUtil.checkSignatureDependencies(this, result, "[I", "Class");
        assertThat(this.requestedClasses.toString(), equalTo("[]"));
        this.clear();
        SignatureUtil.checkSignatureDependencies(this, result, "(Lnet/sf/plugfy/sample/SampleMethodParameter<Lnet/sf/plugfy/sample/SampleMethodParameterType;>;ILjava/lang/String;)Lnet/sf/plugfy/sample/SampleReturn<Lnet/sf/plugfy/sample/SampleMethodReturnType;>;", "Class");
        assertThat(this.requestedClasses.toString(), equalTo("[java.lang.String, net.sf.plugfy.sample.SampleMethodParameter, net.sf.plugfy.sample.SampleMethodParameterType, net.sf.plugfy.sample.SampleMethodReturnType, net.sf.plugfy.sample.SampleReturn]"));

        this.clear();
        SignatureUtil.checkSignatureDependencies(this, result, "<T:Lnet/sf/plugfy/sample/SampleClassTypeParameter;>Lnet/sf/plugfy/sample/SampleParent<Lnet/sf/plugfy/sample/SampleParentClassTypeParameter;>;Lnet/sf/plugfy/sample/SampleIface;", "Class");
        assertThat(this.requestedClasses.toString(), equalTo("[net.sf.plugfy.sample.SampleClassTypeParameter, net.sf.plugfy.sample.SampleIface, net.sf.plugfy.sample.SampleParent, net.sf.plugfy.sample.SampleParentClassTypeParameter]"));
    }

    @Override
    public void clear() {
        this.requestedClasses.clear();

    }

    @Override
    public JavaClass findClass(final String s) {
        return null;
    }

    @Override
    public ClassPath getClassPath() {
        return null;
    }

    @Override
    public JavaClass loadClass(final String s) throws ClassNotFoundException {
        this.requestedClasses.add(s);
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public JavaClass loadClass(final Class class1) throws ClassNotFoundException {
        return null;
    }

    @Override
    public void removeClass(final JavaClass javaclass) {
        // empty
    }

    @Override
    public void storeClass(final JavaClass javaclass) {
        // empty
    }

}
