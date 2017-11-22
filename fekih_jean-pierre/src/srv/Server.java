package srv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import data.Announces;
import data.ListClientData;

public class Server {

	static volatile ListClientData clients = new ListClientData();
	static volatile Announces announces = new Announces();
	
	public static void main(String[] argv) {

		ServerSocket srv = null;
		final int DEFAULT_PORT = 2408;

		int port = (argv.length == 0) ? DEFAULT_PORT : Integer
				.parseInt(argv[0]);

		try {

			srv = new ServerSocket(port);
			System.out.println("Server on port 2408");

			while (true) {

				Socket sock = srv.accept();
				new Thread(new RunClient(sock)).start();
			}

		} catch (IOException io) {

			io.printStackTrace();

		} finally {

			try {

				if (srv != null)
					srv.close();

			} catch (IOException e) {
			}
		}
	}
}
