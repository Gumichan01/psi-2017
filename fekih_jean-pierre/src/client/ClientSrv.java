package client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ClientSrv implements Runnable {

	private int srv_port;
	
	public ClientSrv(int msg_port){
		
		srv_port = msg_port;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ServerSocket srv = null;

		try {

			srv = new ServerSocket(srv_port);
			System.out.println("Server/client on port " + srv_port);

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

}
