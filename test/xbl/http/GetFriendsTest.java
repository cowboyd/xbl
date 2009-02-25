package xbl.http;

import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import xbl.FriendList;
import xbl.Friend;


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
		assertEquals(28, list.size());
	}

	@Test
	public void FindsActiveFriendProperties() {
		Friend friend = list.get(0);
		assertEquals("JKGuy16", friend.getGamerTag());
		assertEquals("http://live.xbox.com/en-US/profile/profile.aspx?pp=0&GamerTag=JKGuy16", friend.getProfileURL());
		assertEquals("http://image.xboxlive.com/global/t.54540809/tile/0/18053", friend.getTileURL());
		assertEquals(10576, friend.getGamerScore());
		assertEquals("Online", friend.getStatus());
		assertEquals("Playing Xbox 360 Dashboard", friend.getInfo());
	}
	 
}
