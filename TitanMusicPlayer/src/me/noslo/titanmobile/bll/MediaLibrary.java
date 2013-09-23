package me.noslo.titanmobile.bll;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import me.noslo.titanmobile.dal.MusicLibraryDAO;

public class MediaLibrary {

	public static final String ARTIST_COLUMN = MediaStore.Audio.Media.ARTIST;
	
	private User user;

	MediaLibrary(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

	public Artist getArtist(Activity activity, int artistId) {
		Uri contentUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
		String[] proj = { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.ARTIST };
		Cursor cursor = activity.getContentResolver().query(contentUri, proj,
				MediaStore.Audio.Artists._ID+ "=?", new String[] {Integer.toString(artistId)}, MediaStore.Audio.Media.ARTIST);
		cursor.moveToFirst();
		return new Artist(cursor.getInt(0), cursor.getString(1));
	}

	public Album getAlbum(Activity activity, int albumId) {
		Uri contentUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
		String[] proj = { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.ALBUM };
		Cursor cursor = activity.getContentResolver().query(contentUri, proj,
				MediaStore.Audio.Albums._ID+ "=?", new String[] {Integer.toString(albumId)}, MediaStore.Audio.Media.ALBUM);
		cursor.moveToFirst();
		return new Album(cursor.getInt(0), cursor.getString(1));
	}

	public Song getSong(int id) {
		// for (Song song : songs) {
		// if (id == song.getId()) {
		// return song;
		// }
		// }
		return null;
	}

	public ArrayList<Artist> getArtists(Activity activity) {
		ArrayList<Artist> artists = new ArrayList<Artist>();
		Cursor cursor = getArtistsCursor( activity );
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Log.d("MediaLibrary",
					cursor.getString(0) + ") " + cursor.getString(1));
			Artist artist = new Artist(cursor.getInt(0), cursor.getString(1));
			artists.add(artist);
			cursor.moveToNext();
		}
		cursor.close();
		return artists;
	}
	
	public Cursor getArtistsCursor( Activity activity ) {
		Uri contentUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
		String[] proj = { MediaStore.Audio.Media._ID,
				ARTIST_COLUMN };
		return activity.getContentResolver().query(contentUri, proj,
				null, null, MediaStore.Audio.Media.ARTIST);
		
	}

	public ArrayList<Album> getAlbums(Activity activity, int artistId) {

		ArrayList<Album> albums = new ArrayList<Album>();
		Cursor cursor = getAlbumsCursor(activity, artistId);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Log.d("MediaLibrary",
					cursor.getString(0) + ") " + cursor.getString(1));
			Album album = new Album(cursor.getInt(0), cursor.getString(1));
			albums.add(album);
			cursor.moveToNext();
		}
		cursor.close();
		return albums;

	}

	private Cursor getAlbumsCursor(Activity activity, int artistId) {
		Uri contentUri;
		if (artistId > 0) {
			contentUri = MediaStore.Audio.Artists.Albums.getContentUri(
					"external", artistId);
		} else {
			contentUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
		}
		String[] proj = { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.ALBUM };
		return activity.getContentResolver().query(contentUri, proj, null,
				null, MediaStore.Audio.Media.ALBUM);

	}

	public void getQueue(User user) {
		MusicLibraryDAO.fetchQueue(user);
	}
	
	 public static void addToPlaylist(Context context, int audioId, int playlistId) {
		 	ContentResolver resolver = context.getContentResolver();

	        String[] cols = new String[] {
	                "count(*)"
	        };
	        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId);
	        Cursor cur = resolver.query(uri, cols, null, null, null);
	        cur.moveToFirst();
	        final int base = cur.getInt(0);
	        cur.close();
	        ContentValues values = new ContentValues();
	        values.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, Integer.valueOf(base + audioId));
	        values.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, audioId);
	        resolver.insert(uri, values);
	    }

}
