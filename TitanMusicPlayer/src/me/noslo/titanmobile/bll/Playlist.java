package me.noslo.titanmobile.bll;

import java.util.ArrayList;

import me.noslo.titanmobile.dal.mediaLibrary.PlaylistDAO;
import me.noslo.titanmobile.dal.mediaLibrary.mediaStore.MediaStorePlaylistDAO;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class Playlist extends BaseMediaList {

	private static final long serialVersionUID = -5221165637894343526L;
	private String mName;
	private long mId;
	private static final String TAG = "Playlist";

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

//	public void add( PlaylistItem playlistItem ) {
//		mPlaylistItems.add( playlistItem );
//	}
	
	@Override
	public String toString() {
		return getName();
	}
}
