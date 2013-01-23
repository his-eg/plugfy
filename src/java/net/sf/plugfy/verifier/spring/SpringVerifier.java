package net.sf.plugfy.verifier.spring;

import java.io.IOException;
import java.net.URL;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.Verifier;
/**
 * 
 * @author markus
 */
public class SpringVerifier implements Verifier {

    @Override
    public void verify(String name, VerificationContext context) throws IOException {
        URL url = context.getClassLoader().getResource(name);
    }

}
