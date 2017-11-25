package srv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;

import misc.AnnounceVisitor;

import data.AnnounceData;
import data.Announces;
import data.ClientData;

import parser.ASTmessage;
import parser.Keyword;
import parser.MessageParser;

public class RunClient implements Runnable, AnnounceVisitor {

	private String anlist = null;
	private Socket sock = null;
	private BufferedReader bf = null;
	private PrintWriter pw = null;
	private ClientData client = null;

	public RunClient(Socket s) {

		sock = s;
		client = new ClientData(sock.getInetAddress());
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
			
			removeClientAnnounces();
			
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
		client.setPort(port);

		Server.clients.add(client);
		return Keyword.CODE + Keyword.COLON + Keyword.CON + Keyword.COLON
				+ Keyword.SUCCESS + Keyword.ENDL;
	}

	// disconnect
	private String evalDisconnection(final ASTmessage ast) {

		removeClientAnnounces();
		return null;
	}

	// list
	private String evalList(final ASTmessage ast) {

		Server.announces.accept(this);
		String response = anlist;
		anlist = null;

		return Keyword.ANLIST + Keyword.COLON + response + Keyword.ENDL;
	}

	// add announce
	private String evalAddAnnounce(final ASTmessage ast) {

		boolean res = Server.announces.addAnnounce(
				ast.getAnnounce().getTitle(), ast.getAnnounce().getText(),
				client.getId());

		if (!res) {
			return Keyword.CODE + Keyword.COLON + Keyword.ANN + Keyword.COLON
					+ Keyword.FAILURE + Keyword.ENDL;
		} else {
			return Keyword.CODE + Keyword.COLON + Keyword.ANN + Keyword.COLON
					+ Keyword.SUCCESS + Keyword.ENDL;
		}
	}

	private String evalDelAnnounce(final ASTmessage ast) {

		int announce_id = ast.getAnnounceID().getId();
		AnnounceData adata = Server.announces.getAnnounce(announce_id);

		if (adata == null) {

			return Keyword.ANNOUNCE + Keyword.COLON + Keyword.DELETE
					+ Keyword.COLON + Keyword.FAILURE + Keyword.ENDL;

		}

		if (adata.getOwner() == client.getId()) {

			adata = null;
			boolean res = Server.announces.removeAnnounce(announce_id);

			if (!res) {
				return Keyword.ANNOUNCE + Keyword.COLON + Keyword.DELETE
						+ Keyword.COLON + Keyword.FAILURE + Keyword.ENDL;
			} else {
				return Keyword.ANNOUNCE + Keyword.COLON + Keyword.DELETE
						+ Keyword.COLON + Keyword.SUCCESS + Keyword.ENDL;
			}

		} else {

			return Keyword.ANNOUNCE + Keyword.COLON + Keyword.DELETE
					+ Keyword.COLON + Keyword.FAILURE + Keyword.ENDL;
		}
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

		int id = ast.getAnnounceID().getId();
		Integer owner = Server.announces.getOwner(id);

		if (owner == null)
			return Keyword.CODE + Keyword.COLON + Keyword.CON + Keyword.COLON
					+ Keyword.FAILURE + Keyword.ENDL;

		ClientData cd = Server.clients.get(owner.intValue());

		if (cd == null)
			return Keyword.CODE + Keyword.COLON + Keyword.CON + Keyword.COLON
					+ Keyword.FAILURE + Keyword.ENDL;

		return Keyword.CLIENT + Keyword.COLON + cd.toString() + Keyword.ENDL;
	}

	private void removeClientAnnounces() {

		Server.announces.removeAllAnnounce(client.getId());

		if (Server.clients.exists(client.getId()))
			Server.clients.delete(client.getId());
	}

	private void respond(String msg) {

		if (msg == null)
			return;

		System.out.println(msg);
		pw.println(msg);
		pw.flush();
	}

	@Override
	public void visit(Collection<AnnounceData> c) {

		Announces ltmp = new Announces();
		Iterator<AnnounceData> it = c.iterator();

		while (it.hasNext()) {

			AnnounceData ad = it.next();
			
			if(Server.clients.exists(ad.getOwner()))
				ltmp.addAnnounce(ad);
		}
		
		anlist = ltmp.toString();
	}
}
