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

    private final VerificationContext context;

    private final String sourceFile;

    /**
     * @param context
     * @param name
     */
    public WebflowVerifierContentHandler(final VerificationContext context, final String name) {
        this.context = context;
        this.sourceFile = name;
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        // verify elements and attributes
        if ("evaluate".equals(qName)) {
            this.handleExpression(attributes.getValue("expression"));
        }
    }

    /**
     * @param value
     */
    private void handleExpression(final String value) {

    }

}
