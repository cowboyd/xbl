package xbox.handlers;

import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import xbox.Friend;
import xbox.ResponseHandler;
import xbox.parse.ElementBuilder;
import xbox.parse.Scope;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Stack;
import java.util.List;


public class FriendList extends ResponseHandler {

	private TopScope top;

	public FriendList() {
		this.top = this.getTopScope();
	}

	public Scope createTopScope() {
		return new TopScope();
	}


	public boolean isSignin() {
		return this.<TopScope>getTopScope().isSignIn();
	}


	public Friend[] getFriends() {
		return this.top.friends.toArray(new Friend[this.top.friends.size()]);
	}

	public static void main(String[] args) throws IOException, SAXException {
		Parser parser = new Parser();
		final FriendList list = new FriendList();
		parser.setContentHandler(list);
		final InputStream asStream = FriendList.class.getResourceAsStream("Friends.aspx.html");
		parser.parse(new InputSource(asStream));
		for (Friend friend : list.getFriends()) {
			System.out.println("friend = " + friend);
		}
	}

	public static class TopScope extends Scope {
		private boolean signIn;
		private LinkedList<Friend> friends;

		public TopScope() {
			super(null);
			this.friends = new LinkedList<Friend>();
		}


		public Scope startElement(ElementBuilder element) {
			if (element.is("td") && element.hasClass("XbcGamerTile")) {
				return new FriendScope(this, this.friends);
			} else {
				return this;
			}
		}

		public Scope endElement(ElementBuilder element) {
			if (element.is("title") && element.getText().startsWith("Sign In")) {
				this.signIn = true;
			}
			return this;
		}

		public boolean isSignIn() {
			return signIn;
		}
	}

	public static class FriendScope extends Scope {
		private Friend friend;

		public FriendScope(Scope parent, List<Friend> friends) {
			super(parent);
			this.friend = new Friend();
			friends.add(this.friend);
		}

		public Scope startElement(ElementBuilder element) {
			if (element.is("img")) {
				if (this.friend.getTileURL() == null) {
					this.friend.setTileURL(element.getAttribute("src"));
				}
			}
			if (element.is("td") && element.hasClass("XbcGamerPresence")) {
				return new PresenceScope(this, friend);
			}
			return this;
		}

		public Scope endElement(ElementBuilder element) {
			if (element.is("a")) {
				this.friend.setGamerTag(element.getText());
				this.friend.setProfileURL(element.getAttribute("href"));
			}
			if (element.is("strong")) {
				this.friend.setGamerScore(new Integer(element.getText()));
			}
			if (element.is("tbody")) {
				return this.getParent();
			}
			if (element.is("p")) {
				String[] strings = element.getStrings();
				if (strings.length > 1) {
					this.friend.setLastSeen(strings[0]);
					this.friend.setLastActivity(strings[1]);
				} else {
					this.friend.setLastSeen(element.getText());
				}
			}
			return this;

		}
	}

	public static class PresenceScope extends Scope {
		private Friend friend;

		public PresenceScope(Scope parent, Friend friend) {
			super(parent);
			this.friend = friend;
		}


		public Scope endElement(ElementBuilder element) {
			if (element.is("h4")) {
				this.friend.setStatus(element.getText());
				return this.getParent();
			}
			return this;
		}
	}
}
