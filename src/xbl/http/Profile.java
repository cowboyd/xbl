package xbl.http;

import org.apache.commons.httpclient.HttpMethod;
import xbl.Friend;

public class Profile extends Response {

    private static String PROFILE_URL = "http://live.xbox.com/en-US/profile/profile.aspx?pp=0&GamerTag=%s";
    private static String AVATAR_URL = "http://avatar.xboxlive.com/avatar/%s/avatarpic-s.png";

    public Profile(HttpMethod response) {
		super(response);
	}

    public static String profileUrlForGamertag(String gamertag)
    {
        return (String.format(PROFILE_URL,gamertag));
    }

    public static String avatarUrlForGamertag(String gamertag)
    {
        return (String.format(AVATAR_URL, gamertag));
    }

    public Friend toFriend()
    {
        Friend friend = new Friend();
        String gamertag = text(findUniqueElement("//*:div[@class='XbcMyXboxCardGamertag']"), "./*:span");
        friend.setGamerTag(gamertag);
        friend.setProfileURL(profileUrlForGamertag(gamertag));
        friend.setTileURL(avatarUrlForGamertag(gamertag));
        friend.setGamerScore(integer(findUniqueElement("//*:div[@class='XbcMyXboxCardGamerscore']"), "./*:span"));
        friend.setStatus(text(findUniqueElement("//*:div[@class='XbcMyXboxCardLeftPane']/*:div[2]"), "./*:span[@class='XbcProfilePresenceStatus'][1]"));
        StringBuffer info = new StringBuffer();
        String info1 = text(findUniqueElement("//*:div[@class='XbcMyXboxCardLeftPane']/*:div[2]"), "./*:span[@class='XbcProfilePresenceStatus'][2]");
        info.append(info1);
        String info2 = text(findUniqueElement("//*:div[@class='XbcMyXboxCardLeftPane']/*:div[2]"), "./*:span[@class='XbcProfilePresenceDetails'][1]");
        if(!"".equals(info2))
        {
            info.append(" - ");
            info.append(info2);
        }
        friend.setInfo(info.toString());

        return (friend);
    }
}
