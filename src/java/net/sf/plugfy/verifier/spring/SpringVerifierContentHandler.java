package net.sf.plugfy.verifier.spring;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.violations.SpringViolation;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * Helper class for parsing the spring bean configurations
 *  
 * @author markus
 */
class SpringVerifierContentHandler extends DefaultHandler {

    private VerificationContext context;
    
    private String file;

    /**
     * Create a handler that relies for checks on the given context
     * 
     * @param context
     * @param name 
     */
    public SpringVerifierContentHandler(VerificationContext context, String name) {
        this.context = context;
        this.file = name;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if("bean".equals(qName)) {
            // check if bean class is available
            checkAvailabilityOfBeanClass(attributes);
            // register bean in context
            String beanId = attributes.getValue("id");
            String beanClazz = attributes.getValue("class");
            this.context.registerBean(beanId, beanClazz);
        }
        
        if("property".equals(qName)) {
            // check referenced bean in context
            String beanId = attributes.getValue("ref");
            this.context.requireBean(SpringViolation.create(this.file, beanId, null));
        }
        
    }

    protected void checkAvailabilityOfBeanClass(Attributes attributes) {
        checkBeanClass(attributes);
        checkBeanFactory(attributes);
        checkParentBean(attributes);
    }

    private void checkParentBean(Attributes attributes) {
        String parentBean = attributes.getValue("parent");
        if(parentBean != null) {
            this.context.requireBean(SpringViolation.create(file, parentBean, null));
        }
    }

    protected void checkBeanFactory(Attributes attributes) {
        String beanFactory = attributes.getValue("factory-bean");
        if(beanFactory != null) {
            this.context.requireBean(SpringViolation.create(file, beanFactory, null));
        }
    }

    protected void checkBeanClass(Attributes attributes) {
        String beanClazz = attributes.getValue("class");
        String beanId = attributes.getValue("id");
        if(beanClazz != null) {
            try {
                context.getClassLoader().loadClass(beanClazz);
            } catch (ClassNotFoundException e) {
                context.getResult().add(SpringViolation.create(this.file, beanId, beanClazz));
            }
        }
    }
    
}
