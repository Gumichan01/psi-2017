package data;

import java.util.HashMap;

public class ListClientData {

	private volatile HashMap<Integer, ClientData> map;

	public ListClientData() {
		map = new HashMap<>();

	}

	public boolean add(ClientData client) {

		synchronized (map) {

			if (!map.containsKey(client.getId())) {

				map.put(client.getId(), client);
				return true;
			}
		}

		return false;
	}

	public boolean exists(int id) {

		boolean b = false;
		;

		synchronized (map) {
			b = map.containsKey(id);
		}

		return b;
	}

	public ClientData get(int id) {

		synchronized (map) {

			if (!map.containsKey(id)) {

				return null;
			}

			return map.get(id);
		}
	}

	public boolean delete(int cid) {

		synchronized (map) {
			
			if (map.containsKey(cid)) {
				map.remove(cid);
				return true;
			}

			return false;
		}
	}
}
