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

public class Playlist {

	private String mName;
	private long mId;
	private ArrayList<PlaylistItem> mPlaylistItems;
	private static final String TAG = "Playlist";

	public Playlist() {
		mPlaylistItems = new ArrayList<PlaylistItem>();
	}

	public Playlist(String name) {
		mPlaylistItems = new ArrayList<PlaylistItem>();
		mName = name;
	}

	public Playlist(long id, String name) {
		mId = id;
		mName = name;
		mPlaylistItems = new ArrayList<PlaylistItem>();
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

	public void add( PlaylistItem playlistItem ) {
		mPlaylistItems.add( playlistItem );
	}
	
	public void addReplaceAll( ArrayList<PlaylistItem> playlistItems ) {
		mPlaylistItems.clear();
		Log.d( TAG, "add replace all playlist: "+playlistItems.size() );
		mPlaylistItems.addAll(playlistItems);
	}
	
	public ArrayList<PlaylistItem> getAll() {
		return mPlaylistItems;
	}

	public int size() {
		return mPlaylistItems.size();
	}

	public PlaylistItem get(int i) {
		return mPlaylistItems.get(i);
	}
	
	@Override
	public String toString() {
		return getName();
	}

	public void remove(PlaylistItem song) {
		mPlaylistItems.remove(song);		
	}
}
