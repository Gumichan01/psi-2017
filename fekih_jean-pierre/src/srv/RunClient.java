package srv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import parser.MessageParser;

import com.sun.imageio.plugins.common.InputStreamAdapter;
import com.sun.xml.internal.bind.v2.TODO;

public class RunClient implements Runnable {

	private Socket sock;

	public RunClient(Socket s) {

		sock = s;
	}

	public void run() {

		boolean keep_going = true;
		String received_message;
		MessageParser parser;


		try {

			BufferedReader bf = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			
			System.out.println("Connection from " + sock.getInetAddress().toString() + ": " + sock.getPort());
			
			while (keep_going) {
				
				received_message = bf.readLine();
				
				if(received_message == null || received_message.isEmpty()) {
					
					keep_going = false;
					sock.close();
					break;
				}

				parser = new MessageParser(received_message);
				
				if(parser.isWellParsed()) {
					
					/// todo RECUPÉRER MESSAGE
				}
				
			}

			sock.close();

		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (NullPointerException ne) {
			ne.printStackTrace();
		}
	}

}
