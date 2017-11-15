package srv;

import java.io.IOException;
import java.net.Socket;

public class RunClient implements Runnable {

	private Socket sock;

	public RunClient(Socket s) {

		sock = s;
	}

	public void run() {

		System.out.println("OK");

		try {

			sock.close();

		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (NullPointerException ne) {
			ne.printStackTrace();
		}
	}

}
