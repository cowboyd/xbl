package xbox;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.Header;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import xbox.parse.Scope;
import xbox.parse.ElementBuilder;

import java.util.List;
import java.util.Stack;


public abstract class ResponseHandler implements ContentHandler {
	protected HttpMethod method;
	private Scope scope;
	private  Scope top;
	private Stack<ElementBuilder> stack = new Stack<ElementBuilder>();
	private ElementBuilder currentElement;

	protected ResponseHandler() {
		this.top  = this.scope = this.createTopScope();
		this.currentElement = stack.push(new ElementBuilder(this.stack.size() - 1, "document", "document", "document", null));
	}

	protected abstract Scope createTopScope();

	public <T extends Scope> T getTopScope() {
		return (T)top;
	}

	public boolean isRedirect() {
		return getRedirectURL() != null;
	}

	public String getRedirectURL() {
		final Header header = method.getResponseHeader("Location");
		return header != null ? header.getValue() : null;
	}

	public void setDocumentLocator(Locator locator) {

	}

	public void startDocument() throws SAXException {

	}

	public void endDocument() throws SAXException {

	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException {

	}

	public void endPrefixMapping(String prefix) throws SAXException {

	}
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		stack.push(this.currentElement = new ElementBuilder(this.stack.size() - 1, uri, localName, qName, attributes));
		//System.out.println(currentElement.getOpenString());
		this.scope = this.scope.startElement(this.currentElement);
	}

	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		//System.out.println(currentElement.getCloseString());
		this.scope = this.scope.endElement(this.currentElement);
		stack.pop();
		this.currentElement = stack.peek();
	}

	public void characters(char[] chars, int start, int length) throws SAXException {
		if (this.currentElement != null) {
			this.currentElement.addChars(chars, start, length);
		}
	}

	public void ignorableWhitespace(char[] chars, int start, int length) throws SAXException {

	}

	public void processingInstruction(String target, String data) throws SAXException {

	}

	public void skippedEntity(String name) throws SAXException {
		
	}

	public static String join(Attributes attributes) {
		if (attributes != null) {
			final int length = attributes.getLength();
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < length; i++) {
				buf.append(attributes.getLocalName(i) + "='" + attributes.getValue(i) + "'");
				if ((i + 1) < length) {
					buf.append(" ");
				}

			}
			return buf.toString();
		} else {
			return "";
		}
	}
}
