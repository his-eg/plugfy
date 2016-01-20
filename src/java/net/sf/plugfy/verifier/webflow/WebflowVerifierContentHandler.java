/* Copyright (c) 2013-2016 HIS eG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may use this file in compliance with the Apache License, Version 2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.plugfy.verifier.webflow;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.el.ExpressionLanguageResolverFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * verifies flow files
 * 
 * @author markus
 */
public class WebflowVerifierContentHandler extends DefaultHandler {

    private final ExpressionLanguageResolverFactory resolver;

    /**
     * @param context
     * @param name
     */
    public WebflowVerifierContentHandler(final VerificationContext context, final String name) {
        this.resolver = new ExpressionLanguageResolverFactory(name, context);
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
        this.resolver.parse(value);
    }

}
