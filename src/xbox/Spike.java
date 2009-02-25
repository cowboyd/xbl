package xbox;

import org.xml.sax.*;

import java.io.IOException;


public class Spike {

	public static void main(String[] args) throws IOException, SAXException, InterruptedException {
//		System.out.print("Getting login form......");
//		HttpClient client = new HttpClient();
//		client.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.0.3) Gecko/2008092414 Firefox/3.0.3");
//		GetMethod get = new GetMethod("http://login.live.com");
//		client.executeMethod(get);
//		Parser parser = new Parser();
//		final SigninForm live = new SigninForm();
//		parser.setContentHandler(live);
//		parser.parse(new InputSource(get.getResponseBodyAsStream()));
//		get.releaseConnection();
//		System.out.println("done");
//
//		Thread.sleep(3L);
//		System.out.print("Posting login....");
//		PostMethod post = new PostMethod(live.getAction());
//		final HashMap<String, String> params = live.getParameters();
//		for (String name: params.keySet()) {
//			post.setParameter(name, params.get(name));
//		}
//		post.setParameter("login", "xbox@cogentdude.com");
//		post.setParameter("passwd", "950lowell");
//
//		client.executeMethod(post);
//		System.out.println("done");
//		System.out.println("Seting Xbox Live Cookies....");
//		//"https://live.xbox.com/xweb/live/passport/setCookies.ashx%3Frru%3DhttpZ3AZ2FZ2FwwwZ2ExboxZ2EcomZ2FenZ2DUSZ2FdefaultZ2Ehtm&lc=1033&cb=F001033httpZ3AZ2FZ2FwwwZ2ExboxZ2EcomZ2FenZ2DUSZ2FdefaultZ2Ehtm&id=66262"
//		dumpResponseHeaders(post);
//		post.releaseConnection();
//		GetMethod friends = new GetMethod("http://live.xbox.com/en-US/profile/FriendList.aspx?WT.svl=nav");
//		client.executeMethod(friends);
//
//		parser = new Parser();
//		final SigninForm setCookieFormContent = new SigninForm();
//		parser.setContentHandler(setCookieFormContent);
//		parser.parse(new InputSource(friends.getResponseBodyAsStream()));
//
//
//		dumpResponseHeaders(friends);
//
//		friends.releaseConnection();
//
//
//		PostMethod setCookies = new PostMethod(setCookieFormContent.getAction());
//		for (String name: setCookieFormContent.getParameters().keySet() ) {
//			setCookies.setParameter(name, setCookieFormContent.getParameters().get(name));
//		}
//		client.executeMethod(setCookies);
//		dumpResponseHeaders(setCookies);
//		setCookies.releaseConnection();
//		System.out.println("Cookies set...");
//
//
//
//		friends = new GetMethod("http://live.xbox.com/en-US/profile/FriendList.aspx?WT.svl=nav");
//		client.executeMethod(friends);
//		System.out.println("friends.getResponseBodyAsString() = " + friends.getResponseBodyAsString());
//		friends.releaseConnection();

	}

	public static String join(Attributes attributes) {
		final int length = attributes.getLength();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buf.append(attributes.getLocalName(i) + "='" + attributes.getValue(i) + "'");
			if ((i + 1) < length) {
				buf.append(", ");
			}

		}
		return buf.toString();
	}

}
