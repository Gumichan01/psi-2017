package client;

import java.net.InetAddress;

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

		// Run the client
		new Thread(new Client(ine, srv_port, msg_port)).start();
		new Thread(new ClientSrv(msg_port)).start();
	}
}
