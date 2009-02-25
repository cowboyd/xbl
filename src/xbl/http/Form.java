package xbl.http;

import org.apache.commons.httpclient.methods.PostMethod;

public interface Form {
	String getActionURL();

	void setPostParameters(PostMethod post);
}
