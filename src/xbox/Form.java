package xbox;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.HashMap;

import xbox.parse.Scope;

/**
 * Created by IntelliJ IDEA.
 * User: cowboyd
 * Date: Nov 2, 2008
 * Time: 12:45:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Form extends ResponseHandler {
	protected String action = "";
	protected HashMap<String, String> parameters = new HashMap<String, String>();

	protected Scope createTopScope() {
		return null;
	}

	public void startElement(String namespace, String qname, String name, Attributes attributes) throws SAXException {
		//System.out.println("<" + name +  " " + Spike.join(attributes) + "/>");
		String upname = name.toUpperCase();
		if ("FORM".equals(upname)) {
			this.action = attributes.getValue("action");
		}
		if ("INPUT".equals(upname)) {
			this.parameters.put(attributes.getValue("name"), attributes.getValue("value"));

		}
	}

	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		//System.out.println("</" + localName + ">");
	}

	public String getAction() {
		return action;
	}

	public HashMap<String, String> getParameters() {
		return parameters;
	}

	public void put(String name, String value) {
		this.parameters.put(name, value);
	}
}
