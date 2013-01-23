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
package net.sf.plugfy.verifier;

import java.net.URL;

import org.apache.bcel.util.Repository;


/**
 * context of a verification run
 *
 * @author hendrik
 */
public class VerificationContext {

    private final VerificationResult result = new VerificationResult();

    private Repository repository;

    private ClassLoader classLoader;

    private final URL underVerification;

    /**
     * VerificationContext
     *
     * @param repository bcel repository
     * @param classLoader class loader
     * @param url the resource under verification
     */
    public VerificationContext(final Repository repository, final ClassLoader classLoader, final URL url) {
        this.repository = repository;
        this.classLoader = classLoader;
        this.underVerification = url;
    }

    /**
     * @return the result
     */
    public VerificationResult getResult() {
        return this.result;
    }

    /**
     * @return the repository
     */
    public Repository getRepository() {
        return this.repository;
    }

    /**
     * @param repository Repository
     */
    public void setRepository(final Repository repository) {
        this.repository = repository;
    }

    /**
     * @return recursive?
     */
    public boolean isRecursive() {
        return false;
    }

    /**
     * @return the classLoader
     */
    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    /**
     * @param classLoader the classLoader to set
     */
    public void setClassLoader(final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public String toString() {
        return "VerificationContext [result=" + this.result + "]";
    }

    /**
     * @return the url of the resource, which is under verification
     */
    public URL getUnderVerification() {
        return this.underVerification;
    }

}
