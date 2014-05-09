package net.sf.plugfy.verifier.spring;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.Verifier;

import org.xml.sax.SAXException;
/**
 * 
 * @author markus
 */
public class SpringVerifier implements Verifier {

    @Override
    public void verify(String name, VerificationContext context) throws IOException {
        InputStream stream = null;
        try {
            URL url = context.getClassLoader().getResource(name);
            if (url == null) {
                // if name is not found in the classpath try to find it in file system 
                File file = new File(name);
                URI uri = file.toURI();
                url = uri.toURL();
            }
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = parserFactory.newSAXParser();
            SpringVerifierContentHandler contentHandler = new SpringVerifierContentHandler(context, name);
            stream = url.openStream();
            saxParser.parse(stream, contentHandler);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

}
