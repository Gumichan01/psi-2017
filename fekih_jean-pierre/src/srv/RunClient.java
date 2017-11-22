package srv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import data.AnnounceData;
import data.ClientData;

import parser.ASTmessage;
import parser.ASTmessage.Type;
import parser.Keyword;
import parser.MessageParser;

public class RunClient implements Runnable {

	private Socket sock = null;
	private BufferedReader bf = null;
	private PrintWriter pw = null;
	private int client_id;

	public RunClient(Socket s) {

		sock = s;
	}

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

					// TODO récupérer la repésentation abstraite du message
					// TODO traiter la demande analysée
					// TODO envoyer la réponse au client
					System.out.println("OK");
					ASTmessage m = parser.getAST();
					System.out.println(m.getType().toString());
					respond(eval(m));

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

		// add announce
		case ANNOUNCE:
			s = evalAddAnnounce(ast);
			break;

		// get the owner of an announce
		case COM:
			s = evalGetOwner(ast);
			break;

		// connection
		case CONNECT:
			s = evalConnection(ast);
			break;

		// remove an announce
		case DEL:
			s = evalDelAnnounce(ast);
			break;

		// disconnection
		case DISCONNECT:
			s = evalDisconnection(ast);
			break;

		// Get announce
		case GET:
			s = evalGetAnnounce(ast);
			break;

		// display list of announces
		case LIST:
			s = evalList(ast);
			break;

		default:
			break;
		}

		return s;
	}

	// connect
	private String evalConnection(final ASTmessage ast) {

		int port = ast.getConnect().getPort();
		InetAddress ine = sock.getInetAddress();

		ClientData cdata = new ClientData(ine, port);
		client_id = cdata.getId();

		Server.clients.add(cdata);
		return Keyword.CODE + Keyword.COLON + Keyword.CON + Keyword.COLON
				+ Keyword.SUCCESS + Keyword.ENDL;
	}

	// disconnect
	private String evalDisconnection(final ASTmessage ast) {

		Server.clients.delete(client_id);
		return null;
	}

	// list
	private String evalList(final ASTmessage ast) {

		return null;
	}

	// add announce
	private String evalAddAnnounce(final ASTmessage ast) {

		return null;
	}

	private String evalDelAnnounce(final ASTmessage ast) {

		return null;
	}

	// get announce
	private String evalGetAnnounce(final ASTmessage ast) {

		int id = ast.getAnnounceID().getId();
		AnnounceData data = Server.announces.getAnnounce(id);

		if (data == null)
			return Keyword.CODE + Keyword.COLON + Keyword.FAILURE
					+ Keyword.ENDL;

		return Keyword.ANNOUNCE + Keyword.COLON + data.toString()
				+ Keyword.ENDL;
	}

	// get client
	private String evalGetOwner(final ASTmessage ast) {

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
