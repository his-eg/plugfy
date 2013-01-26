package net.sf.plugfy.verifier.spring;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.violations.JavaViolation;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * Helper class for parsing the spring bean configurations
 *  
 * @author markus
 */
class SpringBeanContentHandler extends DefaultHandler {

    private VerificationContext context;

    /**
     * Create a handler that relies for checks on the given context
     * 
     * @param context
     */
    public SpringBeanContentHandler(VerificationContext context) {
        this.context = context;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        String beanClazz = attributes.getValue("class");
        if(beanClazz != null) {
            try {
                context.getClassLoader().loadClass(beanClazz);
            } catch (ClassNotFoundException e) {
                context.getResult().add(JavaViolation.create("", beanClazz, null));
            }
        }
    }
    
}
