package xbox.handlers;

import xbox.ResponseHandler;
import xbox.parse.ElementBuilder;
import xbox.parse.Scope;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;
import org.ccil.cowan.tagsoup.Parser;

import java.util.Stack;
import java.io.InputStream;
import java.io.IOException;


public class PrintAll extends ResponseHandler {

	Stack<ElementBuilder> stack = new Stack<ElementBuilder>();

	public PrintAll() {

	}

	protected Scope createTopScope() {
		return new Scope();
	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println(stack.push(el(namespaceURI, localName, qName, attributes)).getOpenString());
	}

	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		System.out.println(stack.pop().getCloseString());
	}

	public void characters(char[] chars, int start, int length) throws SAXException {
		if (stack.size() > 0) {
			stack.peek().addChars(chars, start, length);
		}
	}

	private ElementBuilder el(String namespaceURI, String localName, String qName, Attributes attributes) {
		return new ElementBuilder(0, namespaceURI, localName, qName, attributes);
	}

	public static void main(String[] args) throws IOException, SAXException {
		Parser parser = new Parser();
		PrintAll print = new PrintAll();
		parser.setContentHandler(print);
		final InputStream asStream = FriendList.class.getResourceAsStream("ViewMessages.aspx.html");
		parser.parse(new InputSource(asStream));
	}
}
