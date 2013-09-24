package me.noslo.titanmobile.bll;

import java.util.ArrayList;

import me.noslo.titanmobile.dal.MediaStorePlaylistDAO;
import me.noslo.titanmobile.dal.PlaylistDAO;

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
	private ArrayList<Song> mSongs;
	private long mLastPlayOrder;

	public Playlist() {
		mSongs = new ArrayList<Song>();
		mLastPlayOrder = 0;
	}

	public Playlist(String name) {
		mSongs = new ArrayList<Song>();
		mLastPlayOrder = 0;
		mName = name;
	}

	public Playlist(Context context, long id) {
		mId = id;
		mSongs = new ArrayList<Song>();
		mLastPlayOrder = 0;
		loadId(context, mId);
	}

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	public void loadId(Context context, long id) {
		mSongs.clear();
		Cursor cursor = context.getContentResolver()
				.query(MediaStore.Audio.Playlists.Members.getContentUri("external", id),
						PROJECTION_PLAYLIST_ITEM, null, null,
						MediaStore.Audio.Playlists.Members.PLAY_ORDER);
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
			PlaylistItem playlistItem = new PlaylistItem(song);
			ContentResolver resolver = context.getContentResolver();
			Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", mId);
			ContentValues values = new ContentValues();
			values.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, ++mLastPlayOrder);
			values.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, playlistItem.getSongId());
			resolver.insert(uri, values);

			Cursor cursor = resolver.query(uri, PROJECTION_PLAYLIST_ITEM, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
				playlistItem.setId(cursor.getLong(cursor
						.getColumnIndex(MediaStore.Audio.Playlists.Members._ID)));
				cursor.close();
			}
		}
	}

	public boolean save(Context context) {
		PlaylistDAO dao = new MediaStorePlaylistDAO(context);
		return dao.save(this);
	}

	public void setId(long id) {
		mId = id;
	}

}
