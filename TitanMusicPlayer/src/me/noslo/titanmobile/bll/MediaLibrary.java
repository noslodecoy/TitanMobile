package me.noslo.titanmobile.bll;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import me.noslo.titanmobile.dal.MusicLibraryDAO;

public class MediaLibrary {

	private User user;
	public static String[] ARTIST_PROJECTION = new String[] { MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.ARTIST };

	public static String[] ALBUM_PROJECTION = new String[] { MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST };

	public static String[] SONG_PROJECTION = new String[] { MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
			MediaStore.Audio.Media.TRACK, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA };

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

	public ArrayList<Artist> getArtistArrayList(Context context) {

		ArrayList<Artist> artists = new ArrayList<Artist>();

		ContentResolver contentResolver = context.getContentResolver();
		Uri contentUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
		Cursor cursor = contentResolver.query(contentUri, ARTIST_PROJECTION, null, null,
				MediaStore.Audio.Media.ARTIST_KEY);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Artist artist = new Artist(cursor.getLong(0), cursor.getString(1));
			artists.add(artist);
			cursor.moveToNext();
		}
		cursor.close();
		return artists;
	}

	public ArrayList<Album> getAlbumArrayList(Context context, long artistId) {
		ArrayList<Album> albums = new ArrayList<Album>();
		ContentResolver contentResolver = context.getContentResolver();
		Uri contentUri;
		if (artistId > 0) {
			contentUri = MediaStore.Audio.Artists.Albums.getContentUri("external", artistId);
		} else {
			contentUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
		}
		Cursor cursor = contentResolver.query(contentUri, ALBUM_PROJECTION, null, null,
				MediaStore.Audio.Media.ALBUM_KEY);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			Album album = new Album(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
			albums.add(album);
			cursor.moveToNext();
		}
		cursor.close();
		return albums;
	}

	public ArrayList<Song> getSongArrayList(Context context, long albumId) {
		ContentResolver contentResolver = context.getContentResolver();
		Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String selection;
		String[] selectionArgs;
		String sortOrder;

		ArrayList<Song> songs = new ArrayList<Song>();

		if (albumId > 0) {
			selection = MediaStore.Audio.Media.ALBUM_ID + "=?";
			selectionArgs = new String[] { String.valueOf(albumId) };
			sortOrder = MediaStore.Audio.Media.TRACK;
		} else {
			selection = null;
			selectionArgs = null;
			sortOrder = MediaStore.Audio.Media.TITLE_KEY;
		}

		Cursor cursor = contentResolver.query(contentUri, SONG_PROJECTION, selection,
				selectionArgs, sortOrder);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Song song = new Song(cursor.getLong(0), cursor.getString(1), cursor.getString(2),
					cursor.getInt(3), cursor.getString(4), cursor.getString(5));
			songs.add(song);
			cursor.moveToNext();
		}
		cursor.close();
		return songs;
	}

}
