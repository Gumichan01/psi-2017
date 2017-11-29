package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class MainClient2 {

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

		Socket socket = new Socket(InetAddress.getByName(DEFAULT_SERVER),
				DEFAULT_PORT);
		
		socket.setSoTimeout(4000);

		BufferedReader bf = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(
				socket.getOutputStream()));

		String s = null;
		
		while(true) {

			s = new Scanner(System.in).nextLine();
			pw.println(s);
			pw.flush();

			String str = bf.readLine();
			
			if(str != null && !str.isEmpty())
				System.out.println(str);
		}
	}
}
