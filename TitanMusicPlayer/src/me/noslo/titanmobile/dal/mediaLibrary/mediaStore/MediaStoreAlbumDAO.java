package me.noslo.titanmobile.dal.mediaLibrary.mediaStore;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.Album;
import me.noslo.titanmobile.bll.Artist;
import me.noslo.titanmobile.bll.Playlist;
import me.noslo.titanmobile.dal.mediaLibrary.AlbumDAO;
import me.noslo.titanmobile.dal.mediaLibrary.ArtistDAO;
import android.content.ContentResolver;
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

	public ArrayList<Album> fetchAll() {
		return fetch(null, null, MediaStore.Audio.Media.ALBUM_KEY);
	}

	private ArrayList<Album> fetch(String selectionClause, String[] selectionArgs, String sortOrder) {

		ArrayList<Album> albums = new ArrayList<Album>();

		Cursor cursor = mContext.getContentResolver().query(mUri, PROJECTION_ALBUM,
				selectionClause, selectionArgs, sortOrder);

		if (cursor != null) {
			int idIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
			int artistIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
			int albumIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

			while (cursor.moveToNext()) {
				Album album = new Album(cursor.getLong(idIndex), cursor.getString(artistIndex), cursor.getString(albumIndex));
				albums.add(album);
			}
		} else {
			Log.v(TAG, "Could not query all playlists");
		}
		return albums;

	}

	@Override
	public Album load(long id) {
		String selectionClause = MediaStore.Audio.Media._ID + "=?";
		String[] selectionArgs = { String.valueOf(id) };
		String sortOrder = null;
		ArrayList<Album> albums = fetch( selectionClause, selectionArgs, sortOrder );
		if ( albums.size() > 0 ) {
			return albums.get(0);
		}
		return null;
	}

	@Override
	public ArrayList<Album> fetch(Artist artist) {
		if ( artist == null ) {
			return fetchAll();
		}
		
		ArrayList<Album> albums = new ArrayList<Album>();
		
		Uri uri = MediaStore.Audio.Artists.Albums.getContentUri("external", artist.getId());

		Cursor cursor = mContext.getContentResolver().query(uri, PROJECTION_ALBUM,
				null, null, MediaStore.Audio.Media.ALBUM_KEY);

		if (cursor != null) {
			int idIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
			int artistIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
			int albumIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

			while (cursor.moveToNext()) {
				Album album = new Album(cursor.getLong(idIndex), cursor.getString(artistIndex), cursor.getString(albumIndex));
				albums.add(album);
			}
		} else {
			Log.v(TAG, "Could not query all playlists");
		}
		return albums;
	}

}
