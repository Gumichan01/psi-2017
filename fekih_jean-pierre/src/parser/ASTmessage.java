package parser;

/**
 * ASTmesage is an abstract representation of a message
 * 
 * It is defined as a couple (e,m) :
 * - e is the enumeration that defines the type of the message
 * - m is the representation of the message related to
 * 
 * */
public class ASTmessage {

	public enum Type { CONNECT, CODE, ANLIST, ANNOUNCE, GET, MSG, DISCONECT } 
	
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
	
	// Abstraction of "annonce:get:id"
	public class AnnounceGetter {
		
		private int id;
		
		public int getId() {
			return id;
		}
	}
	
	// Members
	Type type;
	
}
