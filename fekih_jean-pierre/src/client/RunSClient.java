package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import parser.ASTmessage;
import parser.ASTmessage.Type;
import parser.Keyword;
import parser.MessageParser;
import srv.Server;

import data.ClientData;

public class RunSClient implements Runnable {

	private Socket sock = null;
	private BufferedReader bf = null;
	private PrintWriter pw = null;

	public RunSClient(Socket s) {

		sock = s;

	}

	@Override
	public void run() {

		boolean keep_going = true;
		String received_message;
		MessageParser parser;

		try {

			bf = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));

			System.out.println("Connection from "
					+ sock.getInetAddress().toString() + ": " + sock.getPort());

			while (keep_going) {

				received_message = bf.readLine();

				if (received_message == null || received_message.isEmpty()) {

					keep_going = false;
					break;
				}

				parser = new MessageParser(received_message);

				if (parser.isWellParsed()) {

					System.out.println("OK");
					ASTmessage m = parser.getAST();
					System.out.println(m.getType().toString());
					respond(eval(m));
					keep_going = !(m.getType() == Type.DISCONNECT);

				} else
					keep_going = false;
			}

		} catch (IOException ie) {
			ie.printStackTrace();

		} catch (NullPointerException | IllegalArgumentException ne) {
			ne.printStackTrace();

		} finally {

			try {
				sock.close();
			} catch (IOException e) {
			}
		}
	}
	
	private String eval(final ASTmessage ast) {

		String s = null;

		switch (ast.getType()) {

		// connection
		case CONNECT:
			s = evalConnection(ast);
			break;

		// remove an announce
		case MSG:
			s = evalMessage(ast);
			break;

		// disconnection
		case DISCONNECT:
			s = evalDisconnection(ast);
			break;

		default:
			break;
		}

		return s;
	}
	
	// connect
	private String evalConnection(final ASTmessage ast) {

		return Keyword.CODE + Keyword.COLON + Keyword.SUCCESS + Keyword.ENDL;
	}
	
	
	private String evalMessage(final ASTmessage ast) {
		
		return null;
	}
	
	
	// disconnect
	private String evalDisconnection(final ASTmessage ast) {

		return null;
	}
	
	private void respond(String msg) {

		if (msg == null)
			return;

		System.out.println(msg);
		pw.println(msg);
		pw.flush();
	}

}
