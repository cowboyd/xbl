package xbox;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.methods.GetMethod;
import org.xml.sax.XMLReader;

import java.io.IOException;

import nu.xom.*;
import nux.xom.xquery.XQueryUtil;


public class SpikeARoo {
	public static void main(String[] args) throws IOException, ParsingException {

		HttpClient client = new HttpClient();


		GetMethod get = new GetMethod("http://live.xbox.com/en-US/profile/MessageCenter/ViewMessages.aspx");
		get.setFollowRedirects(false);

		try {
			client.executeMethod(get);
			System.out.println("get.getStatusCode() = " + get.getStatusCode());
			for (Header header : get.getResponseHeaders()) {
				System.out.println(header.getName() + ": " + header.getValue());
			}
			XMLReader parser = new org.ccil.cowan.tagsoup.Parser(); // tagsoup parser
			Document doc = new Builder(parser).build(get.getResponseBodyAsStream());
			System.out.println("doc.toXML() = " + doc.toXML());
			Nodes results = XQueryUtil.xquery(doc, "//*:title");
			Node node = results.get(0);
			System.out.println("node.getValue() = " + node.getValue());
//			System.out.println("results.get(0) = " + node.toXML());
//			System.out.println("results = " + results.size());
		} finally {
			get.releaseConnection();
		}



//		Nodes results = XQueryUtil.xquery(doc, "//*:img/@src");
	}
}
