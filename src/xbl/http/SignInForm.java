
/*
 * Copyright (c) 2009. The Frontside Software, Inc.
 *
 * The contents of this file are subject to the Gnu General Public License version 2
 * or later (the "License"); You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package xbl.http;

import nu.xom.Element;
import nu.xom.Nodes;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class SignInForm extends Response implements Form {
	private Element element;
	private String login;
	private String passwd;

	public SignInForm(HttpMethod response) {
		super(response);
		this.element = this.findUniqueElement("//*:form[@name='f1']");
	}

	public void setEmail(String email) {
		this.login = email;
	}

	public void setPassword(String password) {
		this.passwd = password;
	}

	public String getActionURL() {
		return this.element.getAttributeValue("action");
	}

	public void setPostParameters(PostMethod post) {
		Nodes inputs = this.query(this.element, "//*:input");
		for (int i = 0; i < inputs.size(); i++) {
			Element input = (Element) inputs.get(i);
			String name = input.getAttributeValue("name");
			String value = input.getAttributeValue("value");
			post.setParameter(name, value);
		}
		post.setParameter("LoginOptions", "2");
		post.setParameter("passwd", this.passwd);
		post.setParameter("login", this.login);
	}
}

