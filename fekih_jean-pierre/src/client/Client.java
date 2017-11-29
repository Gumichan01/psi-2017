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

public class Client implements Runnable {

	private Socket socket;
	private int msg_port;

	private InetAddress client_addr;
	private int client_port;

	public Client(InetAddress ine, int srv_port, int msg_port)
			throws IOException {

		try {

			this.socket = new Socket(ine, srv_port);
			this.msg_port = msg_port;

		} catch (IOException e) {

			System.err.println(e.getMessage());
			throw e;
		}
	}

	@Override
	public void run() {

		boolean keep_going = true;

		while (keep_going) {

			System.out.println("1: Connect to the server");
			System.out.println("2: Connect to the client");
			System.out.println("3: Quit");

			Scanner sc = new Scanner(System.in);
			int v = sc.nextInt();
			sc.close();

			switch (v) {

			case 1:
				connectServer();
				break;

			case 2:
				connectClient();
				break;

			case 3:
				keep_going = false;
				break;

			default:
				break;
			}

		}
	}

	private void connectServer() {

		try {

			boolean keep_going = true;
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));

			while (keep_going) {

				Scanner sc = new Scanner(System.in);
				String s = sc.nextLine();
				sc.close();

				pw.println(s);
				s = bf.readLine();

				if (s == null || s.isEmpty()) {

					return;
				}

				MessageParser mp = new MessageParser(s);

				if (mp.isWellParsed()) {

				} else {
					return;
				}
			}

		} catch (Exception e) {
		}
	}

	private void connectClient() {

		Socket sock = null;

		try {

			String str;
			boolean keep_going = true;
			sock = new Socket(client_addr, client_port);
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
