package xbox;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.*;

import java.io.IOException;
import java.util.HashMap;

import xbox.handlers.FriendList;
import xbox.handlers.PrintAll;
import xbox.handlers.MessageList;


public class XBoxLive {

	public static final String SIGNIN_URL = "http://login.live.com/login.srf?wa=wsignin1.0&wreply=https://live.xbox.com/xweb/live/passport/setCookies.ashx";
	public static final String FRIENDS_URL = "http://live.xbox.com/en-US/profile/Friends.aspx";
	public static final String MESSAGE_URL = "http://live.xbox.com/en-US/profile/MessageCenter/ViewMessages.aspx?WT.svl=nav";

	HttpClient client = new HttpClient();

	public XBoxLive() {
		client.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.0.3) Gecko/2008092414 Firefox/3.0.3");
	}


	public void signIn(String email, String password) {
		System.out.println("Signing In to Xbox Live.....");
		Form loginForm = doGet(SIGNIN_URL, Form.class);
		System.out.println("Got the login form... set cookies");


		loginForm.put("login", email);
		loginForm.put("passwd", password);

		Form postSignin = doPost(loginForm.getAction(), loginForm.getParameters(), Form.class);


		doPost(postSignin.getAction(), postSignin.getParameters(), Form.class);

		System.out.println("done....");

	}

	public Friend[] getFriends() throws NotSignedInException, TransportException {
		FriendList list = doGet(FRIENDS_URL, FriendList.class);
		if (list.isSignin()) {
			throw new NotSignedInException();
		}
		return list.getFriends();
	}

	public MessageHeader[] getMessages() throws NotSignedInException, TransportException {
		MessageList list = doGet(MESSAGE_URL, MessageList.class);
		return list.getMessageHeaders();
	}

	private <T extends ResponseHandler> T doPost(String action, HashMap<String, String> parameters, Class<? extends T> handlerClass) {
		PostMethod post = new PostMethod(action);
		for (String name : parameters.keySet()) {
			post.setParameter(name, parameters.get(name));
		}
		return doMethod(post, handlerClass);
	}


	private <T extends ResponseHandler> T doGet(String url, Class<? extends T> handlerClass) {
		return doMethod(new GetMethod(url), handlerClass);

	}

	private <T extends ResponseHandler> T doMethod(HttpMethod method, Class<? extends T> handlerClass) {
		Parser parser = new Parser();
		T handler;
		try {
			handler = handlerClass.newInstance();
			parser.setContentHandler(handler);
			handler.method = method;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		try {
			client.executeMethod(method);
		//	dumpResponseHeaders(method);
			parser.parse(new InputSource(method.getResponseBodyAsStream()));
			return handler;
		} catch (IOException e) {
			throw new TransportException(e);
		} catch (SAXException e) {
			throw new TransportException(e);
		} finally {
			method.releaseConnection();
		}
	}



	public static void main(String[] args) {
		XBoxLive live = new XBoxLive();
		live.signIn(args[0], args[1]);
		System.out.println("FRIENDS:");
		for (Friend friend : live.getFriends()) {
			System.out.println("friend = " + friend);
		}

		System.out.println("MESSAGES:");
		for (MessageHeader header : live.getMessages()) {
			System.out.println("header = " + header);
		}




	}

	private static void dumpResponseHeaders(HttpMethod method) {
		for (Header header: method.getResponseHeaders()) {
			System.out.println(header.getName() + ": " + header.getValue());
		}
	}


}
