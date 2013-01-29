/**
 * 
 */
package net.sf.plugfy.verifier.webflow;

import net.sf.plugfy.verifier.VerificationContext;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author markus
 */
public class WebflowVerifierContentHandler extends DefaultHandler {
    
    private VerificationContext context;
    
    private String sourceFile;

    /**
     * @param context
     * @param name
     */
    public WebflowVerifierContentHandler(VerificationContext context, String name) {
        this.context = context;
        this.sourceFile = name;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // verify elements and attributes
    }

}
