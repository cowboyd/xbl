package xbox.handlers;

import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.InputSource;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import xbox.MessageHeader;
import xbox.ResponseHandler;
import xbox.parse.ElementBuilder;
import xbox.parse.Scope;

import java.io.InputStream;
import java.util.ArrayList;


public class MessageList extends ResponseHandler {


	protected Scope createTopScope() {
		return new MessagesScope();
	}

	public MessageHeader[] getMessageHeaders() {
		ArrayList<MessageHeader> headers = this.<MessagesScope>getTopScope().getMessageHeaders();
		return headers.toArray(new MessageHeader[headers.size()]);
	}

	private class MessagesScope extends Scope {
		ArrayList<MessageHeader> messages = new ArrayList<MessageHeader>();


		public ArrayList<MessageHeader> getMessageHeaders() {
			return messages;
		}

		public Scope startElement(ElementBuilder element) {
			if (element.is("tbody")) {
				boolean unread = element.hasClass("XbcMessageUnread");
				boolean read = element.hasClass("XbcMessageRead");
				if (read || unread) {
					MessageHeader header = new MessageHeader();
					header.setRead(read);
					this.messages.add(header);
					return new MessageScope(this, header);
				}
			}
			return this;
		}

	}

	private class MessageScope extends Scope {
		private MessageHeader header;


		public MessageScope(Scope parent, MessageHeader header) {
			super(parent);
			this.header = header;
		}

		public Scope startElement(ElementBuilder element) {
			if (element.hasClass("XbcGamerTile")) {
				return new TileScope(this, header);
			} else if (element.hasClass("XbcGamerTag")) {
				return new GamerTagScope(this, this.header);
			} else if (element.hasClass("XbcMessageListSubject")) {
				return new SummaryScope(this, this.header);
			} else if (element.hasClass("XbcGamerDescription")) {
				return new TimeScope(this, this.header);
			}
			return this;
		}

		public Scope endElement(ElementBuilder element) {
			if (element.is("tbody")) {
				return this.getParent();
			}
			return this;
		}
	}

	private class TileScope extends Scope {

		private MessageHeader header;

		public TileScope(Scope parent, MessageHeader header) {
			super(parent);
			this.header = header;
		}

		public Scope startElement(ElementBuilder element) {
			if (element.is("img")) {
				this.header.setGamerTileURL(element.getAttribute("src"));
			}
			return this;
		}

		public Scope endElement(ElementBuilder element) {
			if (element.is("td")) {
				return this.getParent();
			}
			return this;
		}

	}

	private class GamerTagScope extends Scope {
		private MessageHeader header;

		private GamerTagScope(Scope parent, MessageHeader header) {
			super(parent);
			this.header = header;
		}

		public Scope startElement(ElementBuilder element) {
			if (element.is("a")) {
				try {
					URI uri = new URI(element.getAttribute("href"), false);
					String query = uri.getQuery();
					header.setMessageId(new Integer(query.split("=")[1]));

				} catch (URIException e) {
					throw new RuntimeException(e);
				}
			}
			return this;
		}

		public Scope endElement(ElementBuilder element) {
			if (element.is("a")) {
				this.header.setFrom(element.getText());
			}
			if (element.is("td")) {
				return this.getParent();
			}
			return this;
		}
	}

	private static class SummaryScope extends Scope {
		private MessageHeader header;

		private SummaryScope(Scope parent, MessageHeader header) {
			super(parent);
			this.header = header;
		}


		public Scope endElement(ElementBuilder element) {
			this.header.setSummary(element.getText());
			return this.getParent();
		}
	}

	private static class TimeScope extends Scope {
		private MessageHeader header;
		private TimeScope(Scope parent, MessageHeader header) {
			super(parent);
			this.header = header;
		}


		public Scope endElement(ElementBuilder element) {
			if (element.is("noscript")) {
				if (this.header.getDateString() == null) {
					this.header.setDateString(element.getText());
				} else {
					this.header.setTimeString(element.getText());
				}
			} else if (element.is("td")) {
				return this.getParent();
			}
			return this;
		}
	}

	public static void main(String[] args) throws Exception {
		Parser parser = new Parser();
		final MessageList list = new MessageList();
		parser.setContentHandler(list);
		final InputStream asStream = FriendList.class.getResourceAsStream("ViewMessages.aspx.html");
		parser.parse(new InputSource(asStream));
		for (MessageHeader header : list.getMessageHeaders()) {
			System.out.println("header = " + header);
		}

	}
}
