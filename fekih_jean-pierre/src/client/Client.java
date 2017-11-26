package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable {

	private Socket socket;
	private int msg_port;

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
		// TODO Auto-generated method stub

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

}
