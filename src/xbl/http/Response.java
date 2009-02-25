package xbl.http;

import nu.xom.*;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.Header;
import xbl.error.NetworkException;
import xbl.error.SystemException;

import java.io.IOException;

import nux.xom.xquery.XQueryUtil;

public class Response {

	protected HttpMethod response;
	protected Document document;

	public Response(HttpMethod response) {
		this.response = response;
		try {
			this.document = new Builder(new org.ccil.cowan.tagsoup.Parser()).build(response.getResponseBodyAsStream());
			System.out.println(this.response.getName() + " " + this.response.getStatusCode() + " " + this.response.getStatusText() + ": " + this.response.getURI());
			for (int i = 0; i < this.response.getResponseHeaders().length; i++) {
				Header header = this.response.getResponseHeaders()[i];
				System.out.println("  " + header.getName() + ": " + header.getValue());
			}
			System.out.println("Body:\n" + this.document.toXML());
		} catch (ParsingException e) {
			throw new SystemException("Unable to parse response from XBox Live", e);
		} catch (IOException e) {
			throw new NetworkException(e);
		}
	}

	public boolean isRedirect() {
		return this.response.getStatusCode() >= 300 && this.response.getStatusCode() < 400;
	}

	public boolean isSuccess() {
		return this.response.getStatusCode() < 300 && this.response.getStatusCode() >= 200;
	}

	public String getRedirectLocation() {
		return this.response.getResponseHeader("Location").getValue();
	}

	protected Element queryElement(String xpath) {
		return (Element) query(xpath).get(0);
	}

	protected Nodes query(Node context, String xpath) {
		return XQueryUtil.xquery(context, xpath);
	}

	protected Nodes query(String xpath) {
		return this.query(this.document, xpath);
	}
}
