/*
 * Copyright (c) 2009. The Frontside Software, Inc.
 *
 * The contents of this file are subject to the Gnu General Public License version 2
 * or later (the "License"); You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package xbl.http;

import nu.xom.*;
import nux.xom.xquery.XQueryUtil;
import org.apache.commons.httpclient.HttpMethod;
import xbl.error.NetworkException;
import xbl.error.SystemException;

import java.io.IOException;

public class Response {

	protected HttpMethod response;
	protected Document document;

	public Response(HttpMethod response) {
		this.response = response;
		try {
			this.document = new Builder(new org.ccil.cowan.tagsoup.Parser()).build(response.getResponseBodyAsStream());
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

	protected Nodes query(Node context, String xpath) {
		return XQueryUtil.xquery(context, xpath);
	}

	protected Nodes query(String xpath) {
		return this.query(this.document, xpath);
	}

	protected Element findUniqueElement(String xpath) {
		return findUniqueElement(this.document, xpath);
	}

	protected Element findUniqueElement(Node context, String xpath) {
		return (Element) query(context, xpath).get(0);
	}

	protected int integer(Node context, String xquery) {
		String t = text(context, xquery);
		return t == null || t.trim().equals("") ? 0 : new Integer(t);
	}

	protected String text(Node context, String xquery) {
		Nodes nodes = query(context, xquery);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			if (node instanceof Attribute) {
				Attribute attr = (Attribute)node;
				builder.append(attr.getValue());
			} else {
				for (int j = 0; j < node.getChildCount(); j++) {
					Node child = node.getChild(j);
					if (child instanceof Text) {
						Text text = (Text) child;
						builder.append(text.toXML());
					}
				}
			}
		}

		return builder.toString().trim().replaceAll("\\s+", " ");
	}
}
