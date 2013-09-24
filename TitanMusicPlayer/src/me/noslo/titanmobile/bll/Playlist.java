package me.noslo.titanmobile.bll;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class Playlist {

	private String mName;
	private long mId;
	private ArrayList<Song> mSongs;
	private long mLastPlayOrder;
	private static final String[] PROJECTION_PLAYLIST_ITEM = { MediaStore.Audio.Media._ID,
			MediaStore.Audio.Playlists.Members.AUDIO_ID, MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.TRACK,
			MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA, MediaStore.Audio.Playlists.Members.PLAY_ORDER };

	private static final String[] INSERT_PROJECTION_PLAYLIST = { MediaStore.Audio.Playlists.NAME,
			MediaStore.Audio.Playlists.DATE_ADDED, MediaStore.Audio.Playlists.DATE_MODIFIED };

	public Playlist() {
		mSongs = new ArrayList<Song>();
		mLastPlayOrder = 0;
	}

	public Playlist(long id) {
		mId = id;
		mSongs = new ArrayList<Song>();
		mLastPlayOrder = 0;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	public void loadId(Context context, long id) {
		mSongs.clear();
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Audio.Playlists.Members.getContentUri("external", id),
				PROJECTION_PLAYLIST_ITEM, null, null, MediaStore.Audio.Playlists.Members.PLAY_ORDER);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			PlaylistItem song = new PlaylistItem(cursor.getLong(0), cursor.getLong(1),
					cursor.getString(2), cursor.getString(3), cursor.getInt(4),
					cursor.getString(5), cursor.getString(6));
			mLastPlayOrder = cursor.getLong(7);
			mSongs.add(song);
			cursor.moveToNext();
		}
		cursor.close();
	}

	public long getId() {
		return mId;
	}

	public void add(Context context, Song song) {
		if (mId > 0) {
			PlaylistItem playlistItem = new PlaylistItem( song );
			ContentResolver resolver = context.getContentResolver();
			Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", mId);
			ContentValues values = new ContentValues();
			values.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, ++mLastPlayOrder);
			values.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, playlistItem.getSongId());
			resolver.insert(uri, values);
			
			Cursor cursor = resolver.query(uri, PROJECTION_PLAYLIST_ITEM, null, null, null);
			if (cursor != null) {
				playlistItem.setId( cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members._ID)) );
				cursor.close();
			}
		}
	}

	public boolean commit(Context context) {
		ContentResolver resolver = context.getContentResolver();
		ContentValues mInserts = new ContentValues();
		mInserts.put(MediaStore.Audio.Playlists.NAME, mName);
		mInserts.put(MediaStore.Audio.Playlists.DATE_ADDED, System.currentTimeMillis());
		mInserts.put(MediaStore.Audio.Playlists.DATE_MODIFIED, System.currentTimeMillis());
		Uri uri = resolver.insert(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, mInserts);
		if (uri != null) {
			Cursor cursor = resolver.query(uri, INSERT_PROJECTION_PLAYLIST, null, null, null);
			if (cursor != null) {
				mId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists._ID));
				cursor.close();
				return true;
			}
		}
		return false;
	}

}
