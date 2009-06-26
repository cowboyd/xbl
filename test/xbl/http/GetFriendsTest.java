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
		assertEquals(22, list.size());
	}

	@Test
	public void FindsActiveFriendProperties() {
		Friend friend = list.get(1);
		assertEquals("dan494", friend.getGamerTag());
		assertEquals("http://live.xbox.com/en-US/profile/profile.aspx?pp=0&GamerTag=dan494", friend.getProfileURL());
		assertEquals("http://avatar.xboxlive.com/avatar/Dan494/avatarpic-s.png", friend.getTileURL());
		assertEquals(7355, friend.getGamerScore());
		assertEquals("Offline", friend.getStatus());
		assertEquals("Last seen 06/24/09 playing The Orange Box", friend.getInfo());
	}
	 
}
