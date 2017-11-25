package data;

import java.net.InetAddress;

import parser.Keyword;

public class ClientData {

	private static int uid = 1;

	private int id;
	private InetAddress ip;
	private int port;

	public ClientData(InetAddress in) {

		id = uid++;
		ip = in;
	}

	public ClientData(InetAddress in, int p) {

		this(in);
		port = p;
	}

	public void setPort(final int p) {
		port = p;
	}

	public int getId() {
		return id;
	}

	public int getPort() {
		return port;
	}

	public InetAddress getIP() {
		return ip;
	}

	@Override
	public String toString() {

		return ip.getHostAddress() + Keyword.COLON + port;
	}
}
