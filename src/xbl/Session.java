/*
 * Copyright (c) 2009. The Frontside Software, Inc.
 *
 * The contents of this file are subject to the Gnu General Public License version 2
 * or later (the "License"); You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package xbl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;

import xbl.http.*;
import xbl.error.NetworkException;
import xbl.error.NotSignedInException;
import xbl.error.SystemException;


public class Session {

	public static final String FRIENDS_URL = "http://live.xbox.com/en-US/profile/Friends.aspx";

	private HttpClient client;

	public Session() {
		init();
	}

	private void init() {
		client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.0.3) Gecko/2008092414 Firefox/3.0.3");
	}

	public void signIn(String email, String password) {
		Response friends = get(FRIENDS_URL);
		if (friends.isSuccess()) {
			return; //already logged in
		}
		SystemException.check(friends.isRedirect(), "Unexpected Response from XBox Live while logging in");
		SignInForm signInForm = get(friends.getRedirectLocation(), SignInForm.class);
		signInForm.setEmail(email);
		signInForm.setPassword(password);
		CookieForm cookies = post(signInForm, CookieForm.class);
		//if cookies is the sign in form again, then login failed because incorrect uname/pwd, check w/SignInException
		Response cookiesAreSet = post(cookies);
		//could check that the location is to friends_url to be 100% sure, but we'll half-ass it.
		SystemException.check(cookiesAreSet.isRedirect(), "Unexpected Response");
	}

	public void signOut() {
		init();
	}

	public FriendList getFriends() throws NotSignedInException {
		GetFriends friends = get(FRIENDS_URL, GetFriends.class);
		NotSignedInException.check(friends.isSuccess(), "You must sign in first");
		return friends.list();
	}

	private Response get(String url) {
		return get(url, Response.class);
	}

	private <T extends Response> T get(String url, Class<? extends  T> handler) {
		return connect(new GetMethod(url), handler);
	}

	private <T extends Response> T post(Form form, Class<? extends T> handler) {
		PostMethod post = new PostMethod(form.getActionURL());
		form.setPostParameters(post);
		return connect(post, handler);
	}

	private Response post(Form form) {
		return post(form, Response.class);
	}

	private <T extends Response> T connect(HttpMethod method, Class<? extends T> handler) {
		try {
			method.setFollowRedirects(false);
			client.executeMethod(method);
			@SuppressWarnings({"unchecked"})
			Constructor<T> constructor = (Constructor<T>) handler.getConstructor(HttpMethod.class);
			return constructor.newInstance(method);
		} catch (IOException e) {
			throw new NetworkException(e);
		} catch (NoSuchMethodException e) {
			throw new SystemException(e);
		} catch (InstantiationException e) {
			throw new SystemException(e);
		} catch (IllegalAccessException e) {
			throw new SystemException(e);
		} catch (InvocationTargetException e) {
			throw new SystemException(e);
		} finally {
			method.releaseConnection();
		}
	}

	public static void main(String[] args) {
		Session xbl = new Session();
		xbl.signIn(args[0], args[1]);
		System.out.println("FRIENDS:\n " + xbl.getFriends());
	}
}
