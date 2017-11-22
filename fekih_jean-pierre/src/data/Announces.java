package data;

import java.util.ArrayList;
import java.util.Iterator;
import parser.Keyword;

public class Announces {

	private ArrayList<AnnounceData> announces;

	public Announces() {

		announces = new ArrayList<>();
	}

	@SuppressWarnings("finally")
	synchronized public boolean addAnnounce(String title, String text, int owner) {

		boolean status = false;

		try {

			AnnounceData d = new AnnounceData(title, text, owner);
			announces.add(d);
			status = true;

		} catch (IllegalArgumentException e) {

			e.printStackTrace();
			status = false;

		} catch (Exception e) {

			e.printStackTrace();
			status = false;

		} finally {

			return status;
		}
	}

	synchronized public AnnounceData getAnnounce(int id) {

		AnnounceData found = null;
		Iterator<AnnounceData> it = announces.iterator();

		while (it.hasNext()) {

			found = it.next();

			if (found.getID() == id)
				break;
		}

		return found;
	}

	synchronized public Integer getOwner(int id) {

		AnnounceData found = null;
		Iterator<AnnounceData> it = announces.iterator();

		while (it.hasNext()) {

			found = it.next();

			if (found.getID() == id)
				break;
		}

		return found != null ? found.getOwner() : null;
	}

	synchronized public boolean removeAnnounce(int id) {

		AnnounceData found = null;
		Iterator<AnnounceData> it = announces.iterator();

		while (it.hasNext()) {

			found = it.next();

			if (found.getID() == id)
				break;
		}

		if (found != null) {

			return announces.remove(found);
		}

		return false;
	}

	@Override
	synchronized public String toString() {

		if (announces.isEmpty())
			return "";

		StringBuilder st = new StringBuilder(Keyword.PIPE);

		for (AnnounceData a : announces) {

			st.append(a.getID() + Keyword.SEMICOLON + a.getTitle());
			st.append(Keyword.PIPE);
		}

		return st.toString();
	}
}
