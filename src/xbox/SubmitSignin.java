package xbox;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import xbox.parse.Scope;


public class SubmitSignin extends ResponseHandler {

	protected Scope createTopScope() {
		return null;
	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println("<" + localName +  " " + Spike.join(attributes) + "/>");
	}

	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		System.out.println("</" + localName + ">");
	}
}
