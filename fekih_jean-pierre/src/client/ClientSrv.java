package client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientSrv implements Runnable {

	private int msg_port;

	public ClientSrv(int msg_port) {

		this.msg_port = msg_port;
	}

	@Override
	public void run() {

		ServerSocket srv = null;

		try {

			srv = new ServerSocket(this.msg_port);
			System.out.println("client/client on port " + msg_port);

			while (true) {

				Socket sock = srv.accept();
				new Thread(new RunSClient(sock)).start();
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

	public static void main(String[] args) {

		new Thread(new ClientSrv(2409)).start();
	}

}
