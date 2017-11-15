package parser;

import java.util.regex.Pattern;

public class MessageParser {

	private String msg;
	private boolean parsed;

	public MessageParser(String m) {

		if (m == null)
			throw new NullPointerException("null string");

		if (m.isEmpty())
			throw new IllegalArgumentException("empty string");

		msg = m;
		parsed = false;
		parse();
	}

	private void parse() {

		Pattern p = Pattern.compile(Keyword.COLON);
		String[] tokens = p.split(msg);

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

		}
	}

	private void parseConnect(final String[] tokens) {

		throw new Exception("parseConnect() not implemented yet");
	}

	private void parseCode(final String[] tokens) {

		throw new Exception("parseCode() not implemented yet");
	}

	private void parseListAnnonce(final String[] tokens) {

		throw new Exception("parseListAnnonce() not implemented yet");
	}

	private void parseAnnonce(final String[] tokens) {

		throw new Exception("parseAnnonce() not implemented yet");
	}

	private void parseMessage(final String[] tokens) {

		throw new Exception("parseMessage() not implemented yet");
	}

	private void parseDisconnect(final String[] tokens) {

		throw new Exception("parseDisconnect() not implemented yet");
	}

	public boolean isWellParsed() {

		return parsed;
	}

}
