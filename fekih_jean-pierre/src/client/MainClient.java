package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainClient {

	public static void main(String[] args) throws Exception {

		final int NPARAM = 3;
		final String DEFAULT_SERVER = "localhost";
		final int DEFAULT_PORT = 2408;
		final int DEFAULT_PORT_MSG = 2409;

		InetAddress ine = args.length != NPARAM ? InetAddress
				.getByName(DEFAULT_SERVER) : InetAddress.getByAddress(args[0]
				.getBytes());

		int srv_port = args.length != NPARAM ? DEFAULT_PORT : Integer
				.parseInt(args[1]);
		int msg_port = args.length != NPARAM ? DEFAULT_PORT_MSG : Integer
				.parseInt(args[2]);

		// automatic test

		Socket s = new Socket(ine, srv_port);
		BufferedReader bf = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(
				s.getOutputStream()));

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

		pw.println("annonce:get:3");
		pw.flush();

		str = bf.readLine();
		System.out.println(str);

		pw.println("annonce:com:3");
		pw.flush();

		str = bf.readLine();
		System.out.println(str);

		pw.println("disconnect");
		pw.flush();

		Thread.sleep(500);

		pw.println();
		pw.flush();

		s.close();

		// / TODO interactive mode

	}
}
