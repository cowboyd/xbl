package xbl.http;

import org.apache.commons.httpclient.HttpMethod;

public class GetFriends extends Response {
	public GetFriends(HttpMethod response) {
		super(response);
	}

	public String spew() {
		return this.document.toXML();
	}
}
