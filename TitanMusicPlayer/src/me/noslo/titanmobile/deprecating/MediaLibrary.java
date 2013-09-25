package me.noslo.titanmobile.deprecating;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import me.noslo.titanmobile.bll.User;
import me.noslo.titanmobile.dal.MediaLibraryDAO;

public class MediaLibrary {

	private User user;

	MediaLibrary(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public String toString() {
		return "MusicLibrary";
	}

	public void getQueue(User user) {
		// MusicLibraryDAO.fetchQueue(user);
	}



//	public ArrayList<Song> getSongArrayList(Context context, long albumId) {
//		ContentResolver contentResolver = context.getContentResolver();
//		Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//		String selection;
//		String[] selectionArgs;
//		String sortOrder;
//
//		ArrayList<Song> songs = new ArrayList<Song>();
//
//		if (albumId > 0) {
//			selection = MediaStore.Audio.Media.ALBUM_ID + "=?";
//			selectionArgs = new String[] { String.valueOf(albumId) };
//			sortOrder = MediaStore.Audio.Media.TRACK;
//		} else {
//			selection = null;
//			selectionArgs = null;
//			sortOrder = MediaStore.Audio.Media.TITLE_KEY;
//		}
//
//		Cursor cursor = contentResolver.query(contentUri, SONG_PROJECTION, selection,
//				selectionArgs, sortOrder);
//		cursor.moveToFirst();
//		while (!cursor.isAfterLast()) {
//			Song song = new Song(cursor.getLong(0), cursor.getString(1), cursor.getString(2),
//					cursor.getInt(3), cursor.getString(4), cursor.getString(5));
//			songs.add(song);
//			cursor.moveToNext();
//		}
//		cursor.close();
//		return songs;
//	}

}
