package me.noslo.titanmobile.dal;

import me.noslo.titanmobile.bll.Album;
import me.noslo.titanmobile.bll.Artist;
import me.noslo.titanmobile.bll.Song;
import me.noslo.titanmobile.bll.User;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.titanmusicplayer.R;

public class MusicLibraryDAO {

	static public Context context;
	static private boolean hasFetchedLibrary = false;
	static private SQLiteDatabase db;

	static public void fetchArrayList(User user) {

		// Resources resources = context.getResources();
		// TypedArray artistsTypedArray =
		// resources.obtainTypedArray(R.array.song_artists);
		// TypedArray albumsTypedArray =
		// resources.obtainTypedArray(R.array.song_albums);
		// TypedArray tracksTypedArray =
		// resources.obtainTypedArray(R.array.song_tracks);
		// TypedArray titlesTypedArray =
		// resources.obtainTypedArray(R.array.song_titles);
		//
		// for (int i = 0; i < titlesTypedArray.length(); i++) {
		// String artistName = artistsTypedArray.getString(i);
		// String albumName = albumsTypedArray.getString(i);
		// int trackNumber = tracksTypedArray.getInt(i, 0);
		// String title = titlesTypedArray.getString(i);
		//
		// Artist artist = user.library.addArtist(artistName);
		// Album album = user.library.addAlbum(artist, albumName);
		// Song song = user.library.addSong(i, album, trackNumber, title, "");
		// artist.addAlbum( album );
		// album.add( song );
		// }

		SQLiteDatabase db = getDb();
		scanMedia(user, db);

		// artistsTypedArray.recycle();
		// albumsTypedArray.recycle();
		// tracksTypedArray.recycle();
		// titlesTypedArray.recycle();
	}

	static public void fetchLibrary(User user) {
//		SQLiteDatabase db = getDb();
//		String[] columns = new String[] { LibraryColumns._ID, LibraryColumns.COLUMN_ARTIST, LibraryColumns.COLUMN_ALBUM, LibraryColumns.COLUMN_TRACK, LibraryColumns.COLUMN_TITLE, LibraryColumns.COLUMN_FILE_NAME };
//		Cursor cursor = db.query(LibraryColumns.TABLE_NAME, columns, null, null, null, null, null);
//		cursor.moveToFirst();
//		while (!cursor.isAfterLast()) {
//			Artist artist = user.library.addArtist(cursor.getString(1));
//			Album album = user.library.addAlbum(artist, cursor.getString(2));
//			Song song = user.library.addSong(cursor.getInt(0), album, cursor.getInt(3), cursor.getString(4), cursor.getString(5));
//			artist.addAlbum(album);
//			album.add(song);
//			cursor.moveToNext();
//		}
		hasFetchedLibrary = true;
	}

	public static void scanMedia(User user, SQLiteDatabase db) {
		if (!hasFetchedLibrary) {
			fetchLibrary( user );
		}
		MediaScanner scanner = new MediaScanner(db);
		scanner.scan(user);
	}

	public static boolean login(String username, String password) {
		Resources resources = context.getResources();
		TypedArray credentials = resources.obtainTypedArray(R.array.credentials);

		for (int i = 0; i < credentials.length(); i++) {
			String credential = credentials.getString(i);
			String[] pieces = credential.split(":");
			if (pieces[0].equals(username)) {
				if (pieces[1].equals(password)) {
					credentials.recycle();
					return true;
				}
				return false;
			}
		}
		credentials.recycle();
		return false;
	}

	public static SQLiteDatabase getDb() {
		if (db == null) {
			MediaLibraryDatabaseHelper dbHelper = new MediaLibraryDatabaseHelper(context);
			db = dbHelper.getWritableDatabase();
		}
		return db;
	}
	
	
	public static void fetchQueue( User user ) {
		if (!hasFetchedLibrary) {
			fetchLibrary( user );
		}
		user.queue.empty();
		SQLiteDatabase db = MusicLibraryDAO.getDb();
		String[] columns = new String[] { QueueColumns._ID, QueueColumns.COLUMN_SONG_ID, QueueColumns.COLUMN_WEIGHT };
		Cursor cursor = db.query(QueueColumns.TABLE_NAME, columns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Song song = user.library.getSong(cursor.getInt(1));
			user.queue.add(song);
			cursor.moveToNext();
		}
	}
	
	public static void removeQueueItem( Song song ) {
		db = getDb();
		db.delete(QueueColumns.TABLE_NAME, QueueColumns._ID+" = "+song.getId(), null );
	}
	
	public static long addQueueItem( Song song ) {
		ContentValues values = new ContentValues();
		values.put(QueueColumns.COLUMN_SONG_ID, song.getId());
		values.put(QueueColumns.COLUMN_WEIGHT, 0);
		db = getDb();
		return db.insert(QueueColumns.TABLE_NAME, null, values);
		
	}


}