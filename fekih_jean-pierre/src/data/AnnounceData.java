package data;

import parser.Keyword;

public class AnnounceData {

	private static int uid = 1;

	private int id;
	private String title;
	private String text;
	private int owner;

	public AnnounceData(String t, String txt, int ido) {

		if (t == null || t.isEmpty())
			throw new IllegalArgumentException("Invalid announce: title");

		if (txt == null || txt.isEmpty())
			throw new IllegalArgumentException("Invalid announce: text");

		id = uid++;
		title = t;
		text = txt;
		owner = ido;
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

	public int getOwner() {

		return owner;
	}
	
	@Override
	public String toString() {

		return title + Keyword.COLON + text;
	}

}
