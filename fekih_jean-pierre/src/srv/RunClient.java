package srv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import parser.ASTmessage;
import parser.MessageParser;

public class RunClient implements Runnable {

	private Socket sock = null;
	private BufferedReader bf = null;
	private PrintWriter pw = null;
	
	public RunClient(Socket s) {

		sock = s;
	}

	public void run() {

		boolean keep_going = true;
		String received_message;
		MessageParser parser;

		try {

			bf = new BufferedReader(new InputStreamReader(
					sock.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(
					sock.getOutputStream()));

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

				} else {

					keep_going = false; // pour tester (à enlever)
				}

				//keep_going = false; // pour tester (à enlever)
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
		
		return "echo";
	}
	
	private  void respond(String msg) {
		pw.println(msg);
		pw.flush();
		
	}
}
