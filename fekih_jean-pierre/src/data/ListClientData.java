package data;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

public class ListClientData {

	private HashMap<Integer, ClientData> map;

	public ListClientData() {
		map = new HashMap<>();

	}

	synchronized public boolean add(ClientData client) {

		if (!map.containsKey(client.getId())) {

			map.put(client.getId(), client);
			return true;
		}

		return false;
	}

	synchronized public boolean delete(ClientData client) {

		if (map.containsKey(client.getId())) {
			map.remove(client.getId());
			return true;
		}

		return false;
	}

	public static void main(String[] args) throws UnknownHostException {

		ClientData client1 = new ClientData(InetAddress.getLocalHost(), 5555);
		ClientData client2 = new ClientData(InetAddress.getLocalHost(), 6666);

		ListClientData l = new ListClientData();
		System.out.println(l.add(client1));
		System.out.println(l.add(client2));
		System.out.println(l.add(client2));
		System.out.println(l.map.size());

		System.out.println(l.delete(client1));
		System.out.println(l.delete(client2));
		System.out.println(l.delete(client1));
		System.out.println(l.map.size());
	}
}
