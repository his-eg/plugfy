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

    private final VerificationContext context;

    private final String file;

    /**
     * Create a handler that relies for checks on the given context
     * 
     * @param context
     * @param name
     */
    public SpringVerifierContentHandler(final VerificationContext context, final String name) {
        this.context = context;
        this.file = name;
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        if("bean".equals(qName)) {
            // check if bean class is available
            this.checkAvailabilityOfBeanClass(attributes);
            // register bean in context
            final String beanId = attributes.getValue("id");
            final String beanClazz = attributes.getValue("class");
            this.context.registerBean(beanId, beanClazz);
        }

        if("property".equals(qName)) {
            // check referenced bean in context
            final String beanId = attributes.getValue("ref");
            this.context.requireBean(SpringViolation.create(this.file, beanId, null));
        }
        if ("alias".equals(qName)) {
            final String beanToAlias = attributes.getValue("name");
            final String alias = attributes.getValue("alias");
            this.context.requireBean(SpringViolation.create(this.file, beanToAlias, null));
            this.context.registerAlias(alias, beanToAlias);
        }
    }

    protected void checkAvailabilityOfBeanClass(final Attributes attributes) {
        this.checkBeanClass(attributes);
        this.checkBeanFactory(attributes);
        this.checkParentBean(attributes);
    }

    private void checkParentBean(final Attributes attributes) {
        final String parentBean = attributes.getValue("parent");
        if(parentBean != null) {
            this.context.requireBean(SpringViolation.create(this.file, parentBean, null));
        }
    }

    protected void checkBeanFactory(final Attributes attributes) {
        final String beanFactory = attributes.getValue("factory-bean");
        if(beanFactory != null) {
            this.context.requireBean(SpringViolation.create(this.file, beanFactory, null));
        }
    }

    protected void checkBeanClass(final Attributes attributes) {
        final String beanClazz = attributes.getValue("class");
        final String beanId = attributes.getValue("id");
        if(beanClazz != null) {
            try {
                this.context.getClassLoader().loadClass(beanClazz);
            } catch (final ClassNotFoundException e) {
                this.context.getResult().add(SpringViolation.create(this.file, beanId, beanClazz));
            }
        }
    }

}
