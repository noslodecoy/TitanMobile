package me.noslo.titanmobile.bll;

import java.util.ArrayList;

public class SimpleMediaList extends ArrayList<MediaLibraryItem> implements MediaLibraryList {

	// private static final String TAG = "MediaLibraryList";

	private static final long serialVersionUID = -3434325347800868191L;
	private String mName;
	private long mId;

	public SimpleMediaList() {
		mId = -1;
		mName = "Unknown";
	}

	public SimpleMediaList(String name) {
		mId = -1;
		mName = name;
	}

	public SimpleMediaList(long id, String name) {
		mId = id;
		mName = name;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	public void setId(long id) {
		mId = id;
	}

	public long getId() {
		return mId;
	}

	public void addReplaceAll(ArrayList<MediaLibraryItem> items) {
		clear();
		addAll(items);
	}

	@Override
	public String toString() {
		return getName();
	}

}
