package parser;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ASTmesage is an abstract representation of a message
 * 
 * It is defined as a couple (e,m) : - e is the enumeration that defines the
 * type of the message - m is the representation of the message related to
 * 
 */
public class ASTmessage {

	public enum Type {
		CONNECT, CODE, CLIENT, LIST, ANLIST, ANNOUNCE, GET, DEL, COM, MSG, DISCONNECT
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

	// Abstraction of "annonce:<cmd>:id"
	public class AnnounceID {

		private int id;

		public int getId() {
			return id;
		}
	}

	public class ANList {

		private String[] strings;

		public String[] getAnnounces() {
			return strings;
		}
	}

	public class Client {

		private InetAddress ine;
		private int port;

		public InetAddress getAddr() {
			return ine;
		}

		public int getPort() {
			return port;
		}
	}
	
	// Members
	Type type;
	Connect connect = null;
	Announce announce = null;
	AnnounceID aid = null;
	ANList list = null;
	String code = null;
	Client client = null;

	ASTmessage(Type ty) {

		type = ty;
	}

	ASTmessage(Type ty, int v) {

		this(ty);

		if (ty == Type.CONNECT) {

			connect = new Connect();
			connect.port = v;

		} else {

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

	// msg:text
	public ASTmessage(Type ty, String s) {

		this(ty);
		code = s;
	}

	// annonunce:list
	public ASTmessage(Type ty, String[] ss) {

		this(ty);
		list = new ANList();
		list.strings = ss;
	}

	
	public ASTmessage(Type ty, String addr, int port) throws UnknownHostException {

		this(ty);
		client = new Client();
		client.ine  = InetAddress.getByName(addr);
		client.port = port; 
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

	public String getMSG() {
		return code;
	}

	public ANList getAnnounceList() {

		return list;
	}

	public Client getClient() {

		return client;
	}
	
}
