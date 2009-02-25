package xbl;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Nodes;
import nux.xom.xquery.XQueryUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.xml.sax.XMLReader;

import java.util.HashMap;


public class Session {

	public static final String FRIENDS_URL = "http://live.xbox.com/en-US/profile/Friends.aspx";

	private HttpClient client = new HttpClient();

	public Session() {
		client.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.0.3) Gecko/2008092414 Firefox/3.0.3");
	}

	public void signIn(String email, String password) throws NetworkException, SignInException {
		GetMethod friends = new GetMethod(FRIENDS_URL);
		friends.setFollowRedirects(false);
		try {
			client.executeMethod(friends);
			friends.getResponseBodyAsString(); // throw away this body
			int statusCode = friends.getStatusCode();
			if (statusCode >= 200 && statusCode <= 250) {
				//already signed in
				return;
			} else if (statusCode - 300 < 100) {
				String location = friends.getResponseHeader("Location").getValue();
				GetMethod loginForm = new GetMethod(location);
				HashMap<String, String> values = new HashMap<String, String>();
				PostMethod postLogin = null;
				try {
					client.executeMethod(loginForm);
					XMLReader parser = new org.ccil.cowan.tagsoup.Parser(); // tagsoup parser
					Document doc = new Builder(parser).build(loginForm.getResponseBodyAsStream());
					Element form = (Element) XQueryUtil.xquery(doc, "//*:form[@name='f1']").get(0);
					Nodes inputs = XQueryUtil.xquery(form, "//*:input");

					String action = form.getAttributeValue("action");
					System.out.println("action = " + action);
					postLogin = new PostMethod(action);
					postLogin.setFollowRedirects(false);

					for (int i = 0; i < inputs.size(); i++) {
						Element input = (Element) inputs.get(i);
						String name = input.getAttributeValue("name");
						String value = input.getAttributeValue("value");
						postLogin.setParameter(name, value);
//						System.out.println(name + " -> " + value);
//						System.out.println(input.toXML());


//						System.out.println("inputs.get(i).toXML() = " + input.toXML());
					}

					postLogin.setParameter("login", email);
					postLogin.setParameter("passwd", password);
					postLogin.setParameter("LoginOptions", "2");
					postLogin.setRequestEntity();
					try {
						client.executeMethod(postLogin);
						parser = new org.ccil.cowan.tagsoup.Parser(); // tagsoup parser
						doc = new Builder(parser).build(postLogin.getResponseBodyAsStream());
						Element cookieForm = (Element) XQueryUtil.xquery(doc, "//*:form[@name='fmHF']").get(0);
						PostMethod setCookies = new PostMethod(cookieForm.getAttributeValue("action"));
						inputs = XQueryUtil.xquery(cookieForm, "//*:input");
						for (int i = 0; i < inputs.size(); i++) {
							Element inp = (Element) inputs.get(i);
							setCookies.setParameter(inp.getAttributeValue("name"), inp.getAttributeValue("value"));
						}
						try {
							client.executeMethod(setCookies);
							System.out.println("setCookies.getResponseBodyAsString() = " + setCookies.getResponseBodyAsString());
						} finally {
							setCookies.releaseConnection();
						}
						System.out.println("cookieForm = " + cookieForm.toXML());
					} finally {
						postLogin.releaseConnection();
					}
				} finally {
					loginForm.releaseConnection();
				}

			} else {
				throw new NetworkException("Problem connecting to server: " + statusCode);
			}
		} catch (Exception e) {
			throw new NetworkException(e);
		} finally {
			friends.releaseConnection();
		}
	}

	public void signOut() {
		this.client = new HttpClient();
	}

	public void getMessages() throws NotSignedInException {

	}

	public static void main(String[] args) {
		Session xbl = new Session();
		xbl.signIn("xbox@cogentdude.com", "950lowell");
	}
}
