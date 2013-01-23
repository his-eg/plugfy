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
        VerificationResult result = new VerificationResult();
        clear();
        SignatureUtil.checkSignatureDependencies(this, result, "Lnet/sf/plugfy/sample/SampleStaticField<Lnet/sf/plugfy/sample/SampleStaticFieldParameter;>;");
        assertThat(requestedClasses.toString(), equalTo("[net.sf.plugfy.sample.SampleStaticField, net.sf.plugfy.sample.SampleStaticFieldParameter]"));
        clear();
        SignatureUtil.checkSignatureDependencies(this, result, "I");
        assertThat(requestedClasses.toString(), equalTo("[]"));
        clear();
        SignatureUtil.checkSignatureDependencies(this, result, "[I");
        assertThat(requestedClasses.toString(), equalTo("[]"));
        clear();
        SignatureUtil.checkSignatureDependencies(this, result, "(Lnet/sf/plugfy/sample/SampleMethodParameter<Lnet/sf/plugfy/sample/SampleMethodParameterType;>;ILjava/lang/String;)Lnet/sf/plugfy/sample/SampleReturn<Lnet/sf/plugfy/sample/SampleMethodReturnType;>;");
        assertThat(requestedClasses.toString(), equalTo("[java.lang.String, net.sf.plugfy.sample.SampleMethodParameter, net.sf.plugfy.sample.SampleMethodParameterType, net.sf.plugfy.sample.SampleMethodReturnType, net.sf.plugfy.sample.SampleReturn]"));

        clear();
        SignatureUtil.checkSignatureDependencies(this, result, "<T:Lnet/sf/plugfy/sample/SampleClassTypeParameter;>Lnet/sf/plugfy/sample/SampleParent<Lnet/sf/plugfy/sample/SampleParentClassTypeParameter;>;Lnet/sf/plugfy/sample/SampleIface;");
        assertThat(requestedClasses.toString(), equalTo("[net.sf.plugfy.sample.SampleClassTypeParameter, net.sf.plugfy.sample.SampleIface, net.sf.plugfy.sample.SampleParent, net.sf.plugfy.sample.SampleParentClassTypeParameter]"));

        clear();
        SignatureUtil.checkSignatureDependencies(this, result, "Ljava/util/List<TCHILD;>;");
        assertThat(requestedClasses.toString(), equalTo("[java.util.List]"));

        clear();
        SignatureUtil.checkSignatureDependencies(this, result, "<CHILD:Lde/his/appserver/service/impl/sul/studyhierarchy/container/StudyInfoPermutationTree$TreeNode;>Ljava/lang/Object;");
        assertThat(requestedClasses.toString(), equalTo("[de.his.appserver.service.impl.sul.studyhierarchy.container.StudyInfoPermutationTree$TreeNode, java.lang.Object]"));

    }

    @Override
    public void clear() {
        requestedClasses.clear();

    }

    @Override
    public JavaClass findClass(String s) {
        return null;
    }

    @Override
    public ClassPath getClassPath() {
        return null;
    }

    @Override
    public JavaClass loadClass(String s) throws ClassNotFoundException {
        requestedClasses.add(s);
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public JavaClass loadClass(Class class1) throws ClassNotFoundException {
        return null;
    }

    @Override
    public void removeClass(JavaClass javaclass) {
        // empty
    }

    @Override
    public void storeClass(JavaClass javaclass) {
        // empty
    }

}
