package parser;

/**
 * ASTmesage is an abstract representation of a message
 * 
 * It is defined as a couple (e,m) : - e is the enumeration that defines the
 * type of the message - m is the representation of the message related to
 * 
 * */
public class ASTmessage {

	public enum Type {
		CONNECT, CODE, LIST, ANLIST, ANNOUNCE, GET, MSG, DISCONECT
	}

	// Abstraction of "connect:port"
	public class Connect {

		private int port;

		public int getPort() {

			return port;
		}
	}

	// Abstraction of "annonce:title:text"
	public class Announce {

		private String title;
		private String text;

		public String getTitle() {
			return title;
		}

		public String getText() {
			return text;
		}
	}

	// Abstraction of "annonce:get:id" or "annonce:get:id"
	public class AnnounceID {

		private int id;

		public int getId() {
			return id;
		}
	}

	// Members
	Type type;
	Connect connect = null;
	Announce announce = null;
	AnnounceID aid = null;

	ASTmessage(Type ty) {

		type = ty;
	}

	ASTmessage(Type ty, int v) {

		this(ty);

		if (ty == Type.CONNECT) {

			connect = new Connect();
			connect.port = v;

		} else { // Announce getter

			aid = new AnnounceID();
			aid.id = v;
		}
	}

	ASTmessage(String title, String text) {

		this(Type.ANNOUNCE);
		announce = new Announce();
		announce.title = title;
		announce.text = text;
	}

	public Type getType() {
		return type;
	}

	public Connect getConnect() {
		return connect;
	}

	public Announce getAnnounce() {
		return announce;
	}

	public AnnounceID getAnnounceID() {
		return aid;
	}
}
