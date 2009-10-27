package xbl.http;

import org.apache.commons.httpclient.HttpMethod;
import xbl.Friend;
import xbl.error.SystemException;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: A018189
 * Date: Oct 27, 2009
 * Time: 3:50:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Profile extends Response {

    private static String PROFILE_URL = "http://live.xbox.com/en-GB/profile/profile.aspx?pp=0&GamerTag=";

    public Profile(HttpMethod response) {
		super(response);
	}

    public static String profileUrlForGamertag(String gamertag)
    {
        return (PROFILE_URL + gamertag);
    }

    public Friend toFriend()
    {
        /**
         * TODO:
         */
        return (null);
    }

    public String toFriendString()
    {
        String resp;
        System.out.println("toFriend");
        try
        {
            resp = super.response.getResponseBodyAsString();
        }
        catch(IOException e)
        {
            throw new SystemException("Unable to parse profile response from XBox Live", e);
        }
        return(resp);
    }
}
