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
