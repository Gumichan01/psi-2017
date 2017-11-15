package data;

public class AnnounceData {

	private static int uid = 1;

	private int id;
	private String title;
	private String text;

	public AnnounceData(String t, String txt) {

		if (t == null || t.isEmpty())
			throw new IllegalArgumentException("Invalid announce: title");

		if (txt == null || txt.isEmpty())
			throw new IllegalArgumentException("Invalid announce: text");

		id = uid++;
		title = t;
		text = txt;
	}

	public int getID() {

		return id;
	}

	public String getTitle() {
		
		return title;
	}
	
	public String getText() {
		
		return text;
	}
	
	@Override
	public String toString() {

		return id + ";" + title;
	}

}
