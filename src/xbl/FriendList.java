/*
 * Copyright (c) 2009. The Frontside Software, Inc.
 *
 * The contents of this file are subject to the Gnu General Public License version 2
 * or later (the "License"); You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package xbl;

public class FriendList {
	private Friend[] friends;

	public FriendList(Friend[] friends) {
		this.friends = friends;
		if (this.friends == null) {
			this.friends = new Friend[0];
		}
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder("(");
		for (int i = 0; i < this.friends.length; i++) {
			buffer.append(friends[i].getGamerTag());
			if (i != this.friends.length - 1) {
				buffer.append(",");
			}
		}
		buffer.append(")");
		return buffer.toString();
	}

	public int size() {
		return this.friends.length;
	}

	public Friend get(int i) {
		return friends[i];
	}
}
