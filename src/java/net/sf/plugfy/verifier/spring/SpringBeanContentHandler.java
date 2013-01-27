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
    
    private String file;

    /**
     * Create a handler that relies for checks on the given context
     * 
     * @param context
     * @param name 
     */
    public SpringBeanContentHandler(VerificationContext context, String name) {
        this.context = context;
        this.file = name;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        String beanClazz = attributes.getValue("class");
        if(beanClazz != null) {
            try {
                context.getClassLoader().loadClass(beanClazz);
            } catch (ClassNotFoundException e) {
                context.getResult().add(JavaViolation.create(this.file, beanClazz, null));
            }
        }
    }
    
}
