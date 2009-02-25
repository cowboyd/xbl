package xbox.parse;

import org.xml.sax.Attributes;

import java.util.ArrayList;

import xbox.ResponseHandler;

/**
 * Created by IntelliJ IDEA.
* User: cowboyd
* Date: Nov 5, 2008
* Time: 6:34:33 PM
* To change this template use File | Settings | File Templates.
*/
public class ElementBuilder {
	StringBuffer text = new StringBuffer();
	private int level;
	private String name;
	private String qName;
	private Attributes attributes;
	private String uri;
	private ArrayList<String> strings = new ArrayList<String>();

	public ElementBuilder(int level, String uri, String name, String qName, Attributes attributes) {
		this.level = level;
		this.uri = uri;
		this.name = name;
		this.qName = qName;
		this.attributes = attributes;
	}

	public String getUri() {
		return uri;
	}

	public String getName() {
		return name;
	}

	public String getQName() {
		return qName;
	}

	public Attributes getAttributes() {
		return attributes;
	}


	public void addChars(char[] chars, int offset, int length) {
		String str = new String(chars, offset, length);
		this.strings.add(str);
		this.text.append(str);
	}

	public String getText() {
		return this.text.toString();
	}

	public String[] getStrings() {
		return this.strings.toArray(new String[this.strings.size()]);
	}

	public String getOpenString() {
		return String.format("%s<%s %s>", indent(), this.getName(), ResponseHandler.join(this.getAttributes()));
	}

	public String getCloseString() {
		String indent = indent().toString();
		String printText;
		if (this.hasText()) {
			printText = indent + "   " + this.getText() + "\n";
		} else {
			printText = "";
		}
		return String.format("%s%s</%s>", printText, indent, this.getName());
	}

	private StringBuffer indent() {
		StringBuffer indent = new StringBuffer("");
		for (int i = 0; i < this.level; i++) {
			indent.append("   ");
		}
		return indent;
	}

	public boolean hasText() {
		return this.getText() != null && !this.getText().equals("");
	}

	public boolean is(String name) {
		return name.equalsIgnoreCase(this.getName());
	}

	public boolean hasClass(String className) {
		String cssClass = this.getAttributes().getValue("class");
		return cssClass != null && cssClass.indexOf(className) >= 0;
	}

	public String getAttribute(String name) {
		return this.getAttributes().getValue(name);
	}
}
