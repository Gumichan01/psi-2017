package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import parser.MessageParser;

public class MainClient {

	private static Socket socket = null;
	private static InetAddress ine = null;

	private static int srv_port;
	private static int msg_port;
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws Exception {

		final int NPARAM = 3;
		final String DEFAULT_SERVER = "localhost";
		final int DEFAULT_PORT = 2408;
		final int DEFAULT_PORT_MSG = 2409;

		ine = args.length != NPARAM ? InetAddress.getByName(DEFAULT_SERVER)
				: InetAddress.getByAddress(args[0].getBytes());

		srv_port = args.length != NPARAM ? DEFAULT_PORT : Integer
				.parseInt(args[1]);
		msg_port = args.length != NPARAM ? DEFAULT_PORT_MSG : Integer
				.parseInt(args[2]);

		// Run the client
		boolean keep_going = true;

		new Thread(new ClientSrv(msg_port)).start();
		
		while (keep_going) {

			System.out.println("1: Connect to the server");
			System.out.println("2: Connect to the client");
			System.out.println("3: Quit");

			int v = input.nextInt();
			input.nextLine();

			switch (v) {

			case 1:
				connectServer();
				break;

			case 2:
				connectClient(InetAddress.getLocalHost());
				break;

			case 3:
				keep_going = false;
				break;

			default:
				break;
			}
		}
	}

	private static void connectServer() {

		try {
			boolean keep_going = true;

			socket = new Socket(ine, srv_port);
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));

			while (keep_going) {

				String str = "", s;

				if (socket.isConnected() && !socket.isClosed())
					System.out.println("CONNECTED");
				else
					System.out.println("DISCONNECTED");

				System.out.println("write command");
				
				if(input.hasNextLine())
					str = input.nextLine();

				if (str.isEmpty() || str.equals("\n"))
					continue;

				//System.out.println(str);
				pw.println(str);
				pw.flush();

				s = bf.readLine();

				if (s == null || s.isEmpty()) {

					System.err.println("empty string");
					socket.close();
					continue;
				}

				System.out.println(s);

				MessageParser mp = new MessageParser(s);

				if (mp.isWellParsed()) {

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

	private static void connectClient(InetAddress addr) {

		Socket sock = null;

		try {

			String str;
			boolean keep_going = true;
			sock = new Socket(addr, msg_port);
			sock.setSoTimeout(1000);

			BufferedReader bf = new BufferedReader(new InputStreamReader(
					sock.getInputStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					sock.getOutputStream()));

			while (keep_going) {

				// write command
				pw.println(new Scanner(System.in).nextLine());
				pw.flush();

				try {
					str = bf.readLine();
				} catch (Exception e) {
					str = null;
				}

				if (str != null)
					System.out.println(str);
			}

		} catch (IOException e) {

			e.printStackTrace();

			if (sock != null) {

				try {
					sock.close();
				} catch (Exception e2) {
				}
			}
		}

	}
}
