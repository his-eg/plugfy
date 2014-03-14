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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.plugfy.verifier.violations.SpringViolation;

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

    private boolean recursive = false;

    /** Map from bean identifier to bean class */
    private final Map<String, String> beanDefinitions = new HashMap<String, String>();

    /** Map from bean alias to bean identifier */
    private final Map<String, String> beanAliases = new HashMap<String, String>();

    private final Set<SpringViolation> requiredBeanIds = new HashSet<SpringViolation>();

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
     * VerificationContext
     *
     * @param repository bcel repository
     * @param classLoader class loader
     * @param url the resource under verification
     * @param beanDefinitions present beans
     */
    public VerificationContext(final Repository repository, final ClassLoader classLoader, final URL url, final Map<String, String> beanDefinitions) {
        this(repository, classLoader, url);
        this.beanDefinitions.putAll(beanDefinitions);
    }

    /**
     * VerificationContext
     * 
     * @param repository
     * @param classLoader
     * @param url
     * @param beanDefinitions
     * @param recursive
     */
    public VerificationContext(final Repository repository, final ClassLoader classLoader, final URL url, final Map<String, String> beanDefinitions, boolean recursive) {
        this(repository, classLoader, url, beanDefinitions);
        this.recursive = recursive;
    }

    /**
     * @return the result
     */
    public VerificationResult getResult() {
        return result;
    }

    /**
     * @return the repository
     */
    public Repository getRepository() {
        return repository;
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
        return recursive;
    }

    /**
     * @return the classLoader
     */
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * @param classLoader the classLoader to set
     */
    public void setClassLoader(final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public String toString() {
        return "VerificationContext [verified=" + underVerification.toExternalForm() + ", result=" + result + ", missingBeanIds="+requiredBeanIds+"]";
    }

    /**
     * @return the url of the resource, which is under verification
     */
    public URL getUnderVerification() {
        return underVerification;
    }

    /**
     * Mark a bean as required, which has not yet been found
     * 
     * @param possibleViolation
     */
    public void requireBean(final SpringViolation possibleViolation) {
        if(!beanDefinitions.containsKey(possibleViolation.getBeanId())) {
            if (!beanAliases.containsKey(possibleViolation.getBeanId())) {
                requiredBeanIds.add(possibleViolation);
            }
        }
    }

    /**
     * Register bean definitions
     * 
     * @param beanId
     * @param beanClass
     */
    public void registerBean(final String beanId, final String beanClass) {
        final Iterator<SpringViolation> i = requiredBeanIds.iterator();
        while(i.hasNext()) {
            final SpringViolation v = i.next();
            if(v.getBeanId().equals(beanId)) {
                i.remove();
            }
        }
        beanDefinitions.put(beanId, beanClass);
    }

    /**
     * @param alias
     * @param beanToAlias
     */
    public void registerAlias(final String alias, final String beanToAlias) {
        beanAliases.put(alias, beanToAlias);
    }

}
