package xbl.http;

import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Before;
import org.junit.Test;
import xbl.Friend;
import xbl.FriendList;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;


public class GetFriendsTest {
	private GetFriends friends;
	private FriendList list;

	@Before
	public void parse() {
		friends = new GetFriends(new GetMethod() {
			public InputStream getResponseBodyAsStream() throws IOException {
				return GetFriends.class.getResourceAsStream("friends.html");
			}
		});
		list = friends.list();
	}

	@Test
	public void CorrectlyGetsTheRightNumberOfFriendElements() {
		assertEquals(25, list.size());
	}

	@Test
	public void FindsActiveFriendProperties() {
		Friend friend = list.get(0);  
		assertEquals("proswell", friend.getGamerTag());
		assertEquals("http://live.xbox.com/en-GB/profile/profile.aspx?pp=0&GamerTag=proswell", friend.getProfileURL());
		assertEquals("http://avatar.xboxlive.com/avatar/proswell/avatarpic-s.png", friend.getTileURL());
		assertEquals(3236, friend.getGamerScore());
		assertEquals("Online", friend.getStatus());
        assertEquals("Playing Xbox 360 Dashboard - Watching a video", friend.getInfo());

	}
	 
}
