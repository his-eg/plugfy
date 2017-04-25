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

    private static final boolean DEBUG = false;
    
    enum State {
        WAITING_FOR_TYPE_START,
        WAITING_FOR_TYPE_END,
        WAITING_FOR_TYPEVAR_END;
    }

    /**
     * checks the dependencies for a signature
     *
     * @param repository repository
     * @param result VerificationResult
     * @param entity method or field
     * @param sourceType the type where the check is performed to enable passing it to violations
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
        
        if (DEBUG) System.out.println("signature=" + signature);

        // Parse signature...
        // Some special cases:
        // Ljava/lang/Iterable<Lcom/google/common/base/Predicate<TCHILD;>;>;
        // <UIVALUE:Ljava/lang/Object;MODELVALUE:Ljava/lang/Object;>Ljava/lang/Object;
        // <MODELVALUE:Ljava/lang/Object;>Ljava/lang/Object;Lde/his/core/cm/exa/infrastructure/jsf/components/DropDownList<TMODELVALUE;TMODELVALUE;>;
        // <T::Ljava/io/Serializable;>
        //
        // Strategy:
        // 1. Decompose signature into tokens at "<", ">", "(", ")" or ";".
        //    Java object types are of the form "L<identifier>;" -> we get the last one, too.
        // 2. Handle some type parameters cutting at last ":" (inclusive)
        // 3. Skip primitive types "BCDFIJSZ" and array marker "["
        // 4. Now we should have reached an "L" or a "T". We parse the former and ignore the latter.
        //
        // Trimming is not necessary because signatures do not contain spaces.
        int start = 0;
        for (int i = 0; i < signature.length(); i++) {
            final char chr = signature.charAt(i);
            if (chr=='<' || chr=='>' || chr=='(' || chr==')' || chr==';') {
                String token = signature.substring(start, i);
                int tokenLength = token.length();
                if (tokenLength > 0) {
                    // handle <CHILD:Ljava/lang/String;> and <T::Ljava/io/Serializable;> looking for the last ":"
                    final int colonIndex = token.lastIndexOf(':');
                    int tokenIndex = (colonIndex > -1) ? colonIndex+1 : 0;
                    // skip primitive types "BCDFIJSZ" and array marker "["
                    // (see http://docs.oracle.com/javase/specs/jvms/se8/jvms8.pdf, p.77)
                    while (tokenIndex<tokenLength && "BCDFIJSZ[".indexOf(token.charAt(tokenIndex)) > -1) {
                        tokenIndex++;
                    }
                    // now we should have a type variable marker "T" or an object type marker "L"
                    if (tokenIndex<tokenLength && token.charAt(tokenIndex) == 'L') {
                        // class marker found
                        final String type = token.substring(tokenIndex+1).replace('/', '.');
                        if (DEBUG) System.out.println("   Found type " + type);
                        try {
                            repository.loadClass(type);
                        } catch (final ClassNotFoundException e) {
                            result.add(JavaViolation.create(sourceType, type, null));
                        }
                    } // else: type variables are ignored
                }
                start = i+1;
            }
        }
    }
}
