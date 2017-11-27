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
		// TODO User Interface - interactive mode

		boolean keep_going = true;

		while (keep_going) {

			System.out.println("1: Connect to the server");
			System.out.println("2: Connect to the client");
			System.out.println("3: Quit");

			int v = new Scanner(System.in).nextInt();

			switch (v) {
			case 1:
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

		// automatic test
		try {

			BufferedReader bf = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));

			String str = null;

			pw.println("connect:" + msg_port);
			pw.flush();

			str = bf.readLine();
			System.out.println(str);

			pw.println("annonce:test:client @ " + msg_port);
			pw.flush();

			str = bf.readLine();
			System.out.println(str);

			pw.println("annonce:list");
			pw.flush();

			str = bf.readLine();
			System.out.println(str);

			pw.println("annonce:get:1");
			pw.flush();

			str = bf.readLine();
			System.out.println(str);

			pw.println("annonce:com:1");
			pw.flush();

			str = bf.readLine();
			System.out.println(str);

			pw.println("disconnect");
			pw.flush();

			pw.println();
			pw.flush();

		} catch (IOException e) {

			e.printStackTrace();
		} finally {

			try {
				socket.close();
			} catch (Exception ee) {

			}
		}
	}

	private void connectServer() {

		try {
			
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			
			Scanner sc = new Scanner(System.in);
			String s = sc.nextLine();
			sc.close();
			
			pw.println(s);
			s = bf.readLine();
			
			if(s == null || s.isEmpty()) {
				
				return;
			}
			
			MessageParser mp = new MessageParser(s);
			
			if(mp.isWellParsed()) {
				
				
			} else {
				return;
			}
			
			
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private void connectClient() {

		try {

			String str;
			boolean keep_going = true;
			Socket sock = new Socket(client_addr, client_port);
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
		}

	}
}
