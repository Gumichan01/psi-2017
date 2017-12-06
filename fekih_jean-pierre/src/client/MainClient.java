package client;

import java.io.BufferedReader;
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

		connectServer();
	}

	private static void connectServer() {

		try {
			String str = "", s = "";
			boolean keep_going = false;

			socket = new Socket(ine, srv_port);
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));

			
			System.out.println("Connection to the server ...");
			pw.println("connect:" + msg_port + "\n");
			pw.flush();

			str = bf.readLine();
			
			if (str != null) {
			
				System.out.println(str);
				
				// code:con:ok
				if(str.equals(Keyword.CODE + Keyword.COLON + Keyword.CON + Keyword.COLON + Keyword.SUCCESS))
					System.out.println("connection OK");
				else {
					System.out.println("error connection");
					return;
				}
			}
			
			while (keep_going) {

				//str = bf.readLine();
				//System.out.println(str);

				/*MessageParser mp = new MessageParser(str);

				if (mp.isWellParsed()) {

					ASTmessage ast = mp.getAST();

					switch (ast.getType()) {

					case ANLIST:
						String[] array = ast.getAnnounceList().getAnnounces();

						for (String elem : array)
							System.out.println(elem);
						break;

					case CODE:
						String msg = ast.getMSG();
						System.out.println(msg);
						break;

					default:
						break;
					}

				} else {
					System.err.println("invalid message");
					socket.close();
					return;
				}*/
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
