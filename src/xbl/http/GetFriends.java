package xbl.http;

import nu.xom.Element;
import nu.xom.Nodes;
import org.apache.commons.httpclient.HttpMethod;
import xbl.Friend;
import xbl.FriendList;

import java.util.ArrayList;

public class GetFriends extends Response {
	public GetFriends(HttpMethod response) {
		super(response);
	}

	public FriendList list() {
		Nodes elements = query("//*:table[@class='XbcProfileTable XbcFriendsListTable']/*:tbody");
		ArrayList<Friend> friends = new ArrayList<Friend>(elements.size());
		for (int i = 0; i < elements.size(); i++) {
			Element element = (Element) elements.get(i);
			Friend friend = new Friend();
			friends.add(friend);
			friend.setGamerTag(text(element, "./*/*:td[@headers='GamerTag']/*/*:a"));
			friend.setProfileURL(text(element, "./*/*:td[@headers='GamerTag']/*/*:a/@href"));
			friend.setTileURL(text(element, "./*/*:td[@headers='GamerTile']/*:img/@src"));
			friend.setGamerScore(integer(element, "./*/*:td[@headers='Gamerscore']/*:strong"));
			friend.setStatus(text(element,  "./*/*:td[@headers='Status']/*:h4"));
			friend.setInfo(text(element,  "./*/*:td[@headers='Info']/*:p"));

		}
		return new FriendList(friends.toArray(new Friend[friends.size()]));
	}

}
