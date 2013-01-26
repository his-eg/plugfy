package net.sf.plugfy.verifier.spring;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

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
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = parserFactory.newSAXParser();
            SpringBeanContentHandler contentHandler = new SpringBeanContentHandler(context);
            saxParser.parse(url.openStream(), contentHandler);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

}
