package parser;

import java.util.regex.Pattern;

import parser.ASTmessage.Type;

/**
 * 
 * Client / Server
 * 
 * Server / Client TODO
 * 
 * Client / Client TODO
 * 
 */
public class MessageParser {

	private static int MSG_CONNECT_LENGTH = 2;
	private static int MSG_DISCONNECT_LENGTH = 1;
	private static int MSG_ANNOUNCE_LENGTH = 3;
	private static int MSG_ANLIST_LENGTH = 2;
	private static int MSG_MESSAGE_LENGTH = 2;
	private static int MSG_CODE_LONG_LENGTH = 3;
	private static int MSG_CODE_SHORT_LENGTH = 2;

	private String msg;
	private boolean parsed;
	private ASTmessage ast = null;

	public MessageParser(String m) {

		if (m == null)
			throw new NullPointerException("parser - null string");

		if (m.isEmpty())
			throw new IllegalArgumentException("parser - empty string");

		msg = m;
		parsed = false;
		parse();
	}

	private void parse() {

		Pattern p = Pattern.compile(Keyword.COLON);
		String[] tokens = p.split(msg);

		try {

			switch (tokens[0]) {

			case Keyword.CONNECT:
				parseConnect(tokens);
				break;

			case Keyword.CODE:
				parseCode(tokens);
				break;

			case Keyword.ANLIST:
				parseListAnnonce(tokens);
				break;

			case Keyword.ANNOUNCE:
				parseAnnonce(tokens);
				break;

			case Keyword.MSG:
				parseMessage(tokens);
				break;

			case Keyword.DISCONNECT:
				parseDisconnect(tokens);
				break;

			case Keyword.CLIENT:
				parseClient(tokens);
				break;
				
			default:
				parsed = false;
				break;
			}

		} catch (Exception e) {

			parsed = false;
			e.printStackTrace();
		}
	}

	private void parseConnect(final String[] tokens) throws Exception {

		if (tokens.length == MSG_CONNECT_LENGTH) {

			try {
				int v = Integer.parseInt(tokens[1]);
				ast = new ASTmessage(Type.CONNECT, v);
				parsed = true;

			} catch (NumberFormatException e) {

				parsed = false;
			}
		}
	}

	private void parseCode(final String[] tokens) throws Exception {

		Type ty = Type.CODE;
		String status = null;
		switch (tokens[1]) {
		case Keyword.CON:
		case Keyword.ANN:
		case Keyword.LIST:
			if (tokens.length == MSG_CODE_LONG_LENGTH) {
				status = tokens[2];
			} else {
				parsed = false;
				return;
			}
			break;
		default:
			if (tokens.length == MSG_CODE_SHORT_LENGTH) {
				status = tokens[1];
			} else {
				parsed = false;
				return;
			}
			break;
		}

		if (status.equals(Keyword.SUCCESS) || status.equals(Keyword.FAILURE)) {
			ast = new ASTmessage(ty, status);
			parsed = true;
		} else {
			parsed = false;
		}

	}

	private void parseListRequest(final String[] tokens) throws Exception {

		if (tokens.length == MSG_ANLIST_LENGTH) {

			ast = new ASTmessage(Type.LIST);
			parsed = true;
		}
	}

	private void parseListAnnonce(final String[] tokens) throws Exception {

		String [] strings = new String[tokens.length -1];
		
		for (int i = 0; i < strings.length; ++i) {
			
			strings[i] = tokens[i + 1];
		}
		
		ast = new ASTmessage(Type.ANLIST, strings);
		parsed = true;
	}

	private void parseAnnonce(final String[] tokens) throws Exception {

		switch (tokens[1]) {

		case Keyword.LIST:
			parseListRequest(tokens);
			break;

		case Keyword.GET:
		case Keyword.COM:
		case Keyword.DELETE:
			parseGetAnnonce(tokens);
			break;

		default:
			if (tokens.length == MSG_ANNOUNCE_LENGTH) {

				ast = new ASTmessage(tokens[1], tokens[2]);
				parsed = true;
			}
			break;
		}
	}

	private void parseGetAnnonce(final String[] tokens) throws Exception {

		if (tokens.length == MSG_ANNOUNCE_LENGTH) {

			Type ty = null;

			switch (tokens[1]) {

			case Keyword.GET:
				ty = Type.GET;
				break;
			case Keyword.COM:
				ty = Type.COM;
				break;
			case Keyword.DELETE:
				ty = Type.DEL;
				break;
			}

			try {
				int v = Integer.parseInt(tokens[2]);
				ast = new ASTmessage(ty, v);
				parsed = true;

			} catch (NumberFormatException e) {

				ast = new ASTmessage(ty, tokens[2]);
				parsed = true;
			}
		}
	}

	private void parseClient(final String[] tokens) throws Exception {

		if (tokens.length == 10 || tokens.length == 3) {

			if(tokens.length == 3){
				
				int v = Integer.parseInt(tokens[2]);
				ast = new ASTmessage(Type.CLIENT, tokens[1], v);
			
			} else {

				int v = Integer.parseInt(tokens[tokens.length -1]);
				String s = "";
				
				for(int i = 1; i < tokens.length -2; i++)
					s += tokens[i] + ":";
				
				s += tokens[tokens.length -2];
				ast = new ASTmessage(Type.CLIENT, s, v);
			}
			
			parsed = true;
		}
	}
	
	private void parseMessage(final String[] tokens) throws Exception {

		if (tokens.length == MSG_MESSAGE_LENGTH) {
			ast = new ASTmessage(Type.MSG, tokens[1]);
			parsed = true;
		}
	}
	
	private void parseDisconnect(final String[] tokens) throws Exception {

		if (tokens.length == MSG_DISCONNECT_LENGTH) {

			ast = new ASTmessage(Type.DISCONNECT);
			parsed = true;
		}
	}

	public boolean isWellParsed() {

		return parsed;
	}

	public ASTmessage getAST() {
		return ast;
	}

	public static void main(String[] args) {

		MessageParser p1 = new MessageParser("connect:1523");

		if (p1.isWellParsed()) {

			ASTmessage m1 = p1.getAST();

			System.out.println(m1.getType().toString() + " "
					+ m1.getConnect().getPort());
		} else
			System.err.println("failure p1");

		MessageParser p2 = new MessageParser("annonce:hello:がんばつて Gumichan");

		if (p2.isWellParsed()) {

			ASTmessage m2 = p2.getAST();

			System.out.println(m2.getType() + " " + m2.getAnnounce().getTitle()
					+ " " + m2.getAnnounce().getText());
		} else
			System.err.println("failure p2");

		MessageParser p3 = new MessageParser("annonce:list");

		if (p3.isWellParsed()) {

			ASTmessage m3 = p3.getAST();

			System.out.println(m3.getType());
		} else
			System.err.println("failure p3");

		MessageParser[] p345 = { new MessageParser("annonce:get:24"),
				new MessageParser("annonce:com:24"),
				new MessageParser("annonce:del:24") };

		int i = 0;
		for (MessageParser mp : p345) {

			if (mp.isWellParsed()) {

				ASTmessage m345 = mp.getAST();
				System.out.println(m345.getType() + " "
						+ m345.getAnnounceID().getId());

			} else
				System.err.println("failure p345 -  " + i);

			i++;
		}

		MessageParser p4 = new MessageParser("disconnect");

		if (p4.isWellParsed()) {

			ASTmessage m4 = p4.getAST();
			System.out.println(m4.getType());

		} else
			System.err.println("failure p4");

		MessageParser p5 = new MessageParser("msg:text");

		if (p5.isWellParsed()) {

			ASTmessage m5 = p5.getAST();

			System.out.println(m5.getType() + " " + m5.getMSG());
		} else
			System.err.println("failure p5");

		MessageParser p6 = new MessageParser("code:con:OK");

		if (p6.isWellParsed()) {

			ASTmessage m6 = p6.getAST();

			System.out.println(m6.getType() + " " + m6.getMSG());
		} else
			System.err.println("failure p6");

		MessageParser p7 = new MessageParser("code:list:FAIL");

		if (p7.isWellParsed()) {

			ASTmessage m7 = p7.getAST();

			System.out.println(m7.getType() + " " + m7.getMSG());
		} else
			System.err.println("failure p7");

		MessageParser p8 = new MessageParser("code:ann:OK");

		if (p8.isWellParsed()) {

			ASTmessage m8 = p8.getAST();

			System.out.println(m8.getType() + " " + m8.getMSG());
		} else
			System.err.println("failure p8");

		MessageParser p9 = new MessageParser("code:OK");

		if (p9.isWellParsed()) {

			ASTmessage m9 = p9.getAST();

			System.out.println(m9.getType() + " " + m9.getMSG());
		} else
			System.err.println("failure p9");

		MessageParser p10 = new MessageParser("code:FAIL");

		if (p10.isWellParsed()) {

			ASTmessage m10 = p10.getAST();

			System.out.println(m10.getType() + " " + m10.getMSG());
		} else
			System.err.println("failure p10");
	}

}
