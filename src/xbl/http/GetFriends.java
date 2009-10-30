/*
 * Copyright (c) 2009. The Frontside Software, Inc.
 *
 * The contents of this file are subject to the Gnu General Public License version 2
 * or later (the "License"); You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

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
		Nodes elements = query("//*:table[@class='XbcProfileTable XbcFriendsListTable']/*:tr");
		ArrayList<Friend> friends = new ArrayList<Friend>(elements.size());
		for (int i = 0; i < elements.size(); i++) {
			Element element = (Element) elements.get(i);
			Friend friend = new Friend();
			friends.add(friend);
			friend.setGamerTag(text(element, "./*:td[@headers='GamerTag']/*:a"));
			friend.setProfileURL(text(element, "./*:td[@headers='GamerTag']/*:a/@href"));
			friend.setTileURL(text(element, "./*:td[@headers='GamerTile']/*:img/@src"));
			friend.setGamerScore(integer(element, "./*:td[@headers='Gamerscore']/*:span"));
			friend.setStatus(text(element,  "./*:td[@headers='Status']"));
			friend.setInfo(text(element,  "./*:td[@headers='Info']"));
		}
		return new FriendList(friends.toArray(new Friend[friends.size()]));
	}

}
