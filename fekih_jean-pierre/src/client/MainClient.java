package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import parser.ASTmessage;
import parser.Keyword;
import parser.MessageParser;

public class MainClient {

	private static Socket socket = null;
	private static InetAddress ine = null;

	private static int srv_port;
	private static int msg_port;
	private static Scanner input = new Scanner(System.in);
	private static boolean connect_to_owner = false;
	private static InetAddress ine_owner;
	private static int port_owner;

	public static void main(String[] args) throws Exception {

		final int NPARAM = 3;
		final String DEFAULT_SERVER = "localhost";
		final int DEFAULT_PORT = 2408;
		final int DEFAULT_PORT_MSG = 2409;

		ine = args.length != NPARAM ? InetAddress.getByName(DEFAULT_SERVER)
				: InetAddress.getByName(args[0]);

		srv_port = args.length != NPARAM ? DEFAULT_PORT : Integer
				.parseInt(args[1]);
		msg_port = args.length != NPARAM ? DEFAULT_PORT_MSG : Integer
				.parseInt(args[2]);

		connectServer();
		if (connect_to_owner)
			connectClient();
	}

	private static void connectServer() {

		try {
			String str = "", string_cmd;
			boolean keep_going = true;

			System.out.println("Connection to the server ...");
			socket = new Socket(ine, srv_port);
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));

			pw.println("connect:" + msg_port);
			pw.flush();

			str = bf.readLine();

			if (str != null) {

				System.out.println(str);

				// code:con:ok
				if (str.equals(Keyword.CODE + Keyword.COLON + Keyword.CON
						+ Keyword.COLON + Keyword.SUCCESS))
					System.out.println("connection OK");
				else {
					System.out.println("error connection");
					return;
				}
			}

			while (keep_going) {

				System.out.println("a. Liste des annonces");
				System.out.println("b. Créer nouvelle annonce");
				System.out.println("c. Supprimer annonce");
				System.out.println("d. Récupérer annonce");
				System.out.println("e. Récupérer propriétaire de l'annonce");
				System.out
						.println("f. Se connecter au propriétaire d'une annonce");
				System.out.println("g. Se Déconnecter");

				string_cmd = input.nextLine();

				switch (string_cmd) {

				case "a":
					pw.println(listAnnounce());
					pw.flush();
					break;
				case "b":
					pw.println(createAnnounce());
					pw.flush();
					break;
				case "c":
					pw.println(deleteAnnounce());
					pw.flush();
					break;
				case "d":
					pw.println(getAnnounce());
					pw.flush();
					break;
				case "e":
					pw.println(getAnnounceOwner());
					pw.flush();
					break;

				case "f":
					pw.println(getAnnounceOwner());
					pw.flush();
					connect_to_owner = true;
					break;

				case "g":
					pw.println(disconnect());
					pw.flush();
					keep_going = false;
					break;

				default:
					// System.out.println("not processed: " + string_cmd + " | "
					// + ( string_cmd != null ? string_cmd.length() : "null") +
					// "~");
					break;
				}

				if (keep_going == false) {
					continue;
				}

				do {

					str = bf.readLine();
				} while (str == null || str.isEmpty());

				MessageParser mp = new MessageParser(str);

				if (mp.isWellParsed()) {

					ASTmessage ast = mp.getAST();

					switch (ast.getType()) {

					case ANLIST:
						String[] array = ast.getAnnounceList().getAnnounces();

						for (String elem : array)
							System.out.println(elem);
						break;

					case CODE:
					case DEL:
						String msg = ast.getMSG();
						System.out.println(msg);
						break;

					case ANNOUNCE:
						ASTmessage.Announce a = ast.getAnnounce();
						System.out.println("titre - " + a.getTitle());
						System.out.println("text  - " + a.getText());
						break;

					case CLIENT:
						ASTmessage.Client c = ast.getClient();
						System.out.println("IP    - " + c.getAddr().toString());
						System.out.println("port  - " + c.getPort());

						if (connect_to_owner) {

							System.out.println("\nDisconnect from the server");
							ine_owner = c.getAddr();
							port_owner = c.getPort();
							pw.println(disconnect());
							pw.flush();
							keep_going = false;
						}

						break;

					default:
						System.out.println("~" + str);
						break;
					}

				} else {
					System.err.println("invalid message");
					socket.close();
					return;
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private static void connectClient() {

		System.out.println("Connection to " + ine_owner.toString() + ":" + port_owner + "\n");
		
		try {

			String scmd = null;
			boolean keep_going = true;
			Socket socket = new Socket(ine_owner, port_owner);

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));

			while (keep_going) {

				System.out.println("a. Send a message");
				System.out.println("b. Quit");

				scmd = input.nextLine();

				switch (scmd) {
				case "a":
					String msg = input.nextLine();
					pw.println("msg:" + msg);
					pw.flush();
					break;

				case "b":
					pw.println("disconnect");
					pw.flush();
					keep_going = false;
					break;

				default:
					break;
				}

			}
			
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			System.out.println("Connection closed");
		}

	}

	private static String createAnnounce() {

		String title, text;

		System.out.println("Titre:");
		title = input.nextLine();
		System.out.println("Texte:");
		text = input.nextLine();

		System.out.println(Keyword.ANNOUNCE + Keyword.COLON + title
				+ Keyword.COLON + text + Keyword.ENDL);

		return Keyword.ANNOUNCE + Keyword.COLON + title + Keyword.COLON + text;
	}

	private static String deleteAnnounce() {

		String string_id;
		System.out.println("Identifier of the announce:");
		string_id = input.nextLine();

		return Keyword.ANNOUNCE + Keyword.COLON + Keyword.DELETE
				+ Keyword.COLON + string_id;
	}

	private static String listAnnounce() {

		return Keyword.ANNOUNCE + Keyword.COLON + Keyword.LIST;
	}

	private static String getAnnounce() {

		String string_id;
		System.out.println("Identifier of the announce (to read):");
		string_id = input.nextLine();

		return Keyword.ANNOUNCE + Keyword.COLON + Keyword.GET + Keyword.COLON
				+ string_id;
	}

	private static String getAnnounceOwner() {

		String string_id;
		System.out.println("Identifier of the announce (to get the owner):");
		string_id = input.nextLine();

		return Keyword.ANNOUNCE + Keyword.COLON + Keyword.COM + Keyword.COLON
				+ string_id;
	}

	private static String disconnect() {

		return "disconnect";
	}
}
