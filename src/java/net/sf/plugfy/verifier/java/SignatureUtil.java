package net.sf.plugfy.verifier.java;

import net.sf.plugfy.verifier.VerificationResult;

import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.FieldOrMethod;
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
    public static void checkSignatureDependencies(Repository repository, VerificationResult result, FieldOrMethod entity) {

        // check method signature. And check extended signature without erased types, if defined.
        SignatureUtil.checkSignatureDependencies(repository, result, entity.getSignature());
        for (Attribute attribute : entity.getAttributes()) {
            if (attribute instanceof Signature) {
                Signature sig = (Signature) attribute;
                SignatureUtil.checkSignatureDependencies(repository, result, sig.getSignature());
            }
        }
    }


    /**
     * checks the dependencies for a signature
     *
     * @param repository repository
     * @param result VerificationResult
     * @param signature  signature
     */
    public static void checkSignatureDependencies(Repository repository, VerificationResult result, String signature) {
        if ((signature == null) || signature.isEmpty()) {
            return;
        }
        if (signature.charAt(0) == '(') {
            checkSignatureDependencies(repository, result, signature.substring(1, signature.indexOf(')')));
            checkSignatureDependencies(repository, result, signature.substring(signature.indexOf(')') + 1, signature.length()));
            return;
        }

        State state = State.WAITING_FOR_TYPE_START;
        int start = 0;
        for (int i = 0; i < signature.length(); i++) {
            char chr = signature.charAt(i);
            if (state == State.WAITING_FOR_TYPE_START) {
                if (chr == 'L') {
                    start = i + 1;
                    state = State.WAITING_FOR_TYPE_END;
                }
            } else {
                if (chr == '<' || chr == '>' || chr == ';') {
                    String type = signature.substring(start, i).replace('/', '.');
                    try {
                        repository.loadClass(type);
                    } catch (ClassNotFoundException e) {
                        result.add(type);
                    }
                    state = State.WAITING_FOR_TYPE_START;
                }
            }
        }
    }
}
