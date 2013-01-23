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

import net.sf.plugfy.verifier.VerificationResult;
import net.sf.plugfy.verifier.violations.JavaViolation;

import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.FieldOrMethod;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Signature;
import org.apache.bcel.util.Repository;

/**
 * helper functions for signatures.
 *
 * @author hendrik
 */
class SignatureUtil {

    enum State {
        WAITING_FOR_TYPE_START,
        WAITING_FOR_TYPE_END;
    }

    /**
     * checks the dependencies for a signature
     *
     * @param repository repository
     * @param result VerificationResult
     * @param entity method or field
     */
    public static void checkSignatureDependencies(final Repository repository, final VerificationResult result, final FieldOrMethod entity, final String sourceType) {

        // check method signature. And check extended signature without erased types, if defined.
        SignatureUtil.checkSignatureDependencies(repository, result, entity.getSignature(), sourceType);
        for (final Attribute attribute : entity.getAttributes()) {
            if (attribute instanceof Signature) {
                final Signature sig = (Signature) attribute;
                SignatureUtil.checkSignatureDependencies(repository, result, sig.getSignature(), sourceType);
            }
        }
    }

    /**
     * checks the dependencies for a signature
     *
     * @param repository repository
     * @param result VerificationResult
     * @param entity method or field
     */
    public static void checkSignatureDependencies(final Repository repository, final VerificationResult result, final JavaClass entity) {

        // check method signature. And check extended signature without erased types, if defined.
        for (final Attribute attribute : entity.getAttributes()) {
            if (attribute instanceof Signature) {
                final Signature sig = (Signature) attribute;
                SignatureUtil.checkSignatureDependencies(repository, result, sig.getSignature(), entity.getClassName());
            }
        }
    }


    /**
     * checks the dependencies for a signature
     *
     * @param repository repository
     * @param result VerificationResult
     * @param signature  signature
     * @param sourceType type containing the signature
     */
    public static void checkSignatureDependencies(final Repository repository, final VerificationResult result, final String signature, final String sourceType) {
        if (signature == null || signature.isEmpty()) {
            return;
        }
        if (signature.charAt(0) == '(') {
            checkSignatureDependencies(repository, result, signature.substring(1, signature.indexOf(')')), sourceType);
            checkSignatureDependencies(repository, result, signature.substring(signature.indexOf(')') + 1, signature.length()), sourceType);
            return;
        }

        State state = State.WAITING_FOR_TYPE_START;
        int start = 0;
        for (int i = 0; i < signature.length(); i++) {
            final char chr = signature.charAt(i);
            if (state == State.WAITING_FOR_TYPE_START) {
                if (chr == 'L') {
                    start = i + 1;
                    state = State.WAITING_FOR_TYPE_END;
                }
            } else {
                if (chr == '<' || chr == '>' || chr == ';') {
                    final String type = signature.substring(start, i).replace('/', '.');
                    try {
                        repository.loadClass(type);
                    } catch (final ClassNotFoundException e) {
                        result.add(JavaViolation.create(sourceType, type, null));
                    }
                    state = State.WAITING_FOR_TYPE_START;
                }
            }
        }
    }
}
