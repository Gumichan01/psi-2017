package data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import misc.AnnounceVisitor;

import parser.Keyword;

public class Announces {

	private volatile ArrayList<AnnounceData> announces;

	public Announces() {

		announces = new ArrayList<>();
	}

	public boolean addAnnounce(String title, String text, int owner) {

		return addAnnounce(new AnnounceData(title, text, owner));
	}

	@SuppressWarnings("finally")
	public boolean addAnnounce(final AnnounceData data) {

		boolean status = false;

		try {

			synchronized (announces) {

				announces.add(data);
			}

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

	public AnnounceData getAnnounce(int id) {

		AnnounceData found = null;

		synchronized (announces) {

			Iterator<AnnounceData> it = announces.iterator();

			while (it.hasNext() && found == null) {

				AnnounceData tmp = it.next();

				if (tmp.getID() == id)
					found = tmp;
			}
		}

		return found;
	}

	public Integer getOwner(int id) {

		AnnounceData found = null;

		synchronized (announces) {

			Iterator<AnnounceData> it = announces.iterator();

			while (it.hasNext() && found == null) {

				AnnounceData tmp = it.next();

				if (tmp.getID() == id)
					found = tmp;
			}
		}

		return found != null ? found.getOwner() : null;
	}

	public boolean removeAnnounce(int id) {

		AnnounceData found = null;

		synchronized (announces) {

			Iterator<AnnounceData> it = announces.iterator();

			while (it.hasNext()) {

				found = it.next();

				if (found.getID() == id)
					break;
			}

			if (found != null) {

				return announces.remove(found);
			}
		}

		return false;
	}

	public void removeAllAnnounce(int client_id) {

		List<AnnounceData> ltmp = new ArrayList<>();

		synchronized (announces) {

			for (AnnounceData a : announces) {

				if (a.getOwner() == client_id)
					ltmp.add(a);
			}

			for (AnnounceData atmp : ltmp) {

				announces.remove(atmp);
			}

		}
	}

	public void accept(AnnounceVisitor v) {

		v.visit(announces);
	}

	@Override
	public String toString() {

		if (announces.isEmpty())
			return "";

		StringBuilder st = new StringBuilder(Keyword.PIPE);

		synchronized (announces) {

			for (AnnounceData a : announces) {

				st.append(a.getID() + Keyword.SEMICOLON + a.getTitle());
				st.append(Keyword.PIPE);
			}
		}

		return st.toString();
	}
}
