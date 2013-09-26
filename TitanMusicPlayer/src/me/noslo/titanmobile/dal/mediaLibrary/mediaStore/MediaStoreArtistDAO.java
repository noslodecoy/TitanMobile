package me.noslo.titanmobile.dal.mediaLibrary.mediaStore;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.Artist;
import me.noslo.titanmobile.bll.MediaLibraryItem;
import me.noslo.titanmobile.bll.Playlist;
import me.noslo.titanmobile.dal.mediaLibrary.ArtistDAO;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class MediaStoreArtistDAO implements ArtistDAO {

	public static String[] PROJECTION_ARTIST = new String[] { MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.ARTIST };

	private static String TAG = "MediaStoreArtistDAO";
	private Uri mUri;
	private Context mContext;

	public MediaStoreArtistDAO(Context context) {
		mContext = context;
		mUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
	}

	public ArrayList<MediaLibraryItem> fetchAll() {
		return fetch(null, null, MediaStore.Audio.Media.ARTIST_KEY);
	}

	private ArrayList<MediaLibraryItem> fetch(String selectionClause, String[] selectionArgs, String sortOrder) {

		ArrayList<MediaLibraryItem> artists = new ArrayList<MediaLibraryItem>();

		Cursor cursor = mContext.getContentResolver().query(mUri, PROJECTION_ARTIST,
				selectionClause, selectionArgs, sortOrder);

		if (cursor != null) {
			int idIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
			int nameIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

			while (cursor.moveToNext()) {
				Artist artist = new Artist(cursor.getLong(idIndex), cursor.getString(nameIndex));
				artists.add(artist);
			}
		} else {
			Log.v(TAG, "Could not query all playlists");
		}
		cursor.close();

		return artists;

	}

	@Override
	public Artist load(long id) {
		String selectionClause = MediaStore.Audio.Media._ID + "=?";
		String[] selectionArgs = { String.valueOf(id) };
		String sortOrder = null;

		Cursor cursor = mContext.getContentResolver().query(mUri, PROJECTION_ARTIST,
				selectionClause, selectionArgs, sortOrder);

		if (cursor == null) {
			Log.v(TAG, "Could not load artist");
		} else if (cursor.getCount() > 0) {
			int idIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
			int nameIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
			cursor.moveToFirst();
			Artist artist = new Artist(cursor.getLong(idIndex), cursor.getString(nameIndex));
			cursor.close();
			return artist;
		}
		cursor.close();
		return null;
	}

}
