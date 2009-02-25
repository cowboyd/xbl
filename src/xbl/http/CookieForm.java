package xbl.http;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.HttpMethod;
import nu.xom.Element;
import nu.xom.Nodes;

public class CookieForm extends Response implements Form {
	private Element element;

	public CookieForm(HttpMethod response) {
		super(response);
		this.element = findUniqueElement("//*:form[@name='fmHF']");
	}

	public String getActionURL() {
		return this.element.getAttributeValue("action");
	}

	public void setPostParameters(PostMethod post) {
		Nodes inputs = query(this.element, "//*:input");
		for (int i = 0; i < inputs.size(); i++) {
			Element input = (Element) inputs.get(i);
			post.setParameter(input.getAttributeValue("name"), input.getAttributeValue("value"));
		}
	}
}
