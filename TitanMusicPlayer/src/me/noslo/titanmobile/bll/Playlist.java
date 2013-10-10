package me.noslo.titanmobile.bll;

public class Playlist extends SimpleMediaList {

	private static final long serialVersionUID = -5221165637894343526L;
	private String mName;
	private long mId;

	public Playlist() {
		mId = -1;
		mName = "";
	}

	public Playlist(String name) {
		mId = -1;
		mName = name;
	}

	public Playlist(long id, String name) {
		mId = id;
		mName = name;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
	}
	
	@Override
	public String toString() {
		return getName();
	}


}
