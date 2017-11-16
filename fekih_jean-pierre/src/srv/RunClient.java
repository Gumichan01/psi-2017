package srv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import parser.MessageParser;

public class RunClient implements Runnable {

	private Socket sock;

	public RunClient(Socket s) {

		sock = s;
	}

	public void run() {

		boolean keep_going = true;
		String received_message;
		MessageParser parser;

		try {

			BufferedReader bf = new BufferedReader(new InputStreamReader(
					sock.getInputStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					sock.getOutputStream()));

			System.out.println("Connection from "
					+ sock.getInetAddress().toString() + ": " + sock.getPort());

			while (keep_going) {

				received_message = bf.readLine();

				if (received_message == null || received_message.isEmpty()) {

					keep_going = false;
					sock.close();
					break;
				}

				parser = new MessageParser(received_message);

				if (parser.isWellParsed()) {

					// TODO récupérer la repésentation abstraite du message
					// TODO traiter la demande analysée
					// TODO envoyer la réponse au client

				} else {

					// TODO envoi message d'erreur
				}

				keep_going = false; // pour tester (à enlever)
			}

			sock.close();

		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (NullPointerException | IllegalArgumentException ne) {
			ne.printStackTrace();
		}
	}
}
