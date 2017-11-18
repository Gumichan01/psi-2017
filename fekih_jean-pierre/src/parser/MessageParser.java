package parser;

import java.util.regex.Pattern;

import parser.ASTmessage.Type;

public class MessageParser {

	private static int MSG_CONNECT_LENGTH = 2;
	private static int MSG_ANNOUNCE_LENGTH = 3;
	private static int MSG_REQLIST_LENGTH = 2;
	private static int MSG_ANLIST_LENGTH = 2;

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

			case Keyword.LIST:
				parseListRequest(tokens);
				break;

			case Keyword.ANLIST:
				parseListAnnonce(tokens);
				break;

			case Keyword.ANNOUNCE:
				parseAnnonce(tokens);
				break;

			case Keyword.GET:
				parseGetAnnonce(tokens);
				break;

			case Keyword.MSG:
				parseMessage(tokens);
				break;

			case Keyword.DISCONNECT:
				parseDisconnect(tokens);
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

		throw new Exception("parseCode() not implemented yet");
	}

	private void parseListRequest(final String[] tokens) throws Exception {

		if (tokens.length == MSG_REQLIST_LENGTH) {

			ast = new ASTmessage(Type.LIST);
			parsed = true;
		}
	}

	private void parseListAnnonce(final String[] tokens) throws Exception {

		throw new Exception("parseListAnnonce() not implemented yet");
	}

	private void parseAnnonce(final String[] tokens) throws Exception {

		if (tokens.length == MSG_ANNOUNCE_LENGTH) {

			ast = new ASTmessage(tokens[1], tokens[2]);
			parsed = true;
		}
	}

	private void parseGetAnnonce(final String[] tokens) throws Exception {

		throw new Exception("parseGetAnnonce() not implemented yet");
	}

	private void parseMessage(final String[] tokens) throws Exception {

		throw new Exception("parseMessage() not implemented yet");
	}

	private void parseDisconnect(final String[] tokens) throws Exception {

		throw new Exception("parseDisconnect() not implemented yet");
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

	}

}
