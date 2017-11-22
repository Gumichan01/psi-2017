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
	synchronized public boolean addAnnounce(String title, String text) {

		boolean status = false;

		try {

			AnnounceData d = new AnnounceData(title, text);
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

		StringBuilder st = new StringBuilder(Keyword.PIPE);

		for (AnnounceData a : announces) {

			st.append(a.toString());
			st.append(Keyword.PIPE);
		}

		return st.toString();
	}

}
