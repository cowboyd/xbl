package xbox.parse;

import xbox.handlers.FriendList;

/**
 * Created by IntelliJ IDEA.
* User: cowboyd
* Date: Nov 5, 2008
* Time: 6:35:37 PM
* To change this template use File | Settings | File Templates.
*/
public class Scope {
	private Scope parent;

	public Scope(Scope parent) {
		this.parent = parent;
	}

	public Scope() {
		this(null);
	}

	public Scope getParent() {
		return parent;
	}

	public Scope startElement(ElementBuilder element) {
		return this;
	}

	public Scope endElement(ElementBuilder element) {
		return this;
	}

}
