package me.noslo.titanmobile.dal.mediaLibrary.mediaStore;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.Album;
import me.noslo.titanmobile.bll.Artist;
import me.noslo.titanmobile.bll.MediaLibraryItem;
import me.noslo.titanmobile.dal.mediaLibrary.AlbumDAO;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class MediaStoreAlbumDAO implements AlbumDAO {

	public static String[] PROJECTION_ALBUM = new String[] { MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM };

	private static String TAG = "MediaStoreAlbumDAO";
	private Uri mUri;
	private Context mContext;

	public MediaStoreAlbumDAO(Context context) {
		mContext = context;
		mUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
	}

	public ArrayList<MediaLibraryItem> fetchAll() {
		return fetch(null, null, MediaStore.Audio.Media.ALBUM_KEY);
	}

	private ArrayList<MediaLibraryItem> fetch(String selectionClause, String[] selectionArgs, String sortOrder) {

		ArrayList<MediaLibraryItem> albums = new ArrayList<MediaLibraryItem>();

		Cursor cursor = mContext.getContentResolver().query(mUri, PROJECTION_ALBUM,
				selectionClause, selectionArgs, sortOrder);

		if (cursor != null) {
			int idIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
			int artistIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
			int albumIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

			while (cursor.moveToNext()) {
				Album album = new Album(cursor.getLong(idIndex), cursor.getString(artistIndex),
						cursor.getString(albumIndex));
				albums.add(album);
			}
		} else {
			Log.v(TAG, "Could not query all playlists");
		}

		cursor.close();

		return albums;

	}

	@Override
	public Album load(long id) {
		String selectionClause = MediaStore.Audio.Media._ID + "=?";
		String[] selectionArgs = { String.valueOf(id) };
		String sortOrder = null;
		ArrayList<MediaLibraryItem> albums = fetch(selectionClause, selectionArgs, sortOrder);
		if (albums.size() > 0) {
			return (Album) albums.get(0);
		}
		return null;
	}

	@Override
	public ArrayList<MediaLibraryItem> fetch(Artist artist) {
		if (artist == null) {
			return fetchAll();
		}

		ArrayList<MediaLibraryItem> albums = new ArrayList<MediaLibraryItem>();

		Uri uri = MediaStore.Audio.Artists.Albums.getContentUri("external", artist.getId());

		Cursor cursor = mContext.getContentResolver().query(uri, PROJECTION_ALBUM, null, null,
				MediaStore.Audio.Media.ALBUM_KEY);

		if (cursor != null) {
			int idIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
			int artistIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
			int albumIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

			while (cursor.moveToNext()) {
				Album album = new Album(cursor.getLong(idIndex), cursor.getString(artistIndex),
						cursor.getString(albumIndex));
				albums.add(album);
			}
		} else {
			Log.v(TAG, "Could not query all playlists");
		}

		cursor.close();

		return albums;
	}

}
