package xbl.http;

import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Before;
import org.junit.Test;
import xbl.Friend;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;


public class GetProfileTest {
    private Profile profile;

	@Before
	public void parse() {
		profile = new Profile(new GetMethod() {
			public InputStream getResponseBodyAsStream() throws IOException {
				return GetFriends.class.getResourceAsStream("profile.html");
			}
		});
	}


	@Test
	public void FindFriendProperties() {
		Friend friend = profile.toFriend();
        assertEquals("Playing Xbox 360 Dashboard - Watching a video", friend.getInfo());
		assertEquals("proswell", friend.getGamerTag());
		assertEquals("http://live.xbox.com/en-US/profile/profile.aspx?pp=0&GamerTag=proswell", friend.getProfileURL());
		assertEquals("http://avatar.xboxlive.com/avatar/proswell/avatarpic-s.png", friend.getTileURL());
		assertEquals(3236, friend.getGamerScore());
		assertEquals("Online", friend.getStatus());

	}

}
