package net.sf.plugfy.verifier.java;

import net.sf.plugfy.verifier.VerificationResult;

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
//        Lnet/sf/plugfy/sample/SampleStaticField<Lnet/sf/plugfy/sample/SampleStaticFieldParameter;>;
//        I
//        [I
//        (Lnet/sf/plugfy/sample/SampleMethodParameter<Lnet/sf/plugfy/sample/SampleMethodParameterType;>;ILjava/lang/String;)Lnet/sf/plugfy/sample/SampleReturn<Lnet/sf/plugfy/sample/SampleMethodReturnType;>;
//        Lnet/sf/plugfy/sample/SampleMethodParameter<Lnet/sf/plugfy/sample/SampleMethodParameterType;>;ILjava/lang/String;
//        Lnet/sf/plugfy/sample/SampleReturn<Lnet/sf/plugfy/sample/SampleMethodReturnType;>;
    }
}
