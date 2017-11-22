package data;

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

	synchronized public ClientData get(int id) {

		if (!map.containsKey(id)) {

			return null;
		}

		return map.get(id);
	}

	synchronized public boolean delete(int cid) {

		if (map.containsKey(cid)) {
			map.remove(cid);
			return true;
		}

		return false;
	}
}
