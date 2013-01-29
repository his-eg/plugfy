package net.sf.plugfy.verifier.webflow;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.Verifier;
/**
 * Verifier for webflow definitions
 *  
 * @author markus
 */
public class WebflowVerifier implements Verifier {

    @Override
    public void verify(String name, VerificationContext context) throws IOException {
        try {
            URL url = context.getClassLoader().getResource(name);
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = parserFactory.newSAXParser();
            WebflowVerifierContentHandler contentHandler = new WebflowVerifierContentHandler(context, name);
            saxParser.parse(url.openStream(), contentHandler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

}
