package me.noslo.titanmobile.dal.mediaLibrary.mediaStore;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import me.noslo.titanmobile.bll.Playlist;
import me.noslo.titanmobile.dal.MediaLibraryDAO;
import me.noslo.titanmobile.dal.mediaLibrary.PlaylistDAO;

public class MediaStorePlaylistDAO implements PlaylistDAO {

	private Context mContext;

	private static final String[] PROJECTION_PLAYLIST = { MediaStore.Audio.Playlists._ID,
			MediaStore.Audio.Playlists.NAME, MediaStore.Audio.Playlists.DATE_ADDED,
			MediaStore.Audio.Playlists.DATE_MODIFIED };

	private static final String TAG = "MediaStorePlaylistDAO";

	private Uri mUri;
	private MediaLibraryDAO mLibrary;

	public MediaStorePlaylistDAO(MediaLibraryDAO library, Context context) {
		mContext = context;
		mLibrary = library;
		mUri = MediaStore.Audio.Playlists.getContentUri("external");
	}

	@Override
	public boolean create(Playlist playlist) {
		ContentValues newValues = new ContentValues();

		newValues.put(MediaStore.Audio.Playlists.NAME, playlist.getName());
		newValues.put(MediaStore.Audio.Playlists.DATE_ADDED, System.currentTimeMillis());
		newValues.put(MediaStore.Audio.Playlists.DATE_MODIFIED, System.currentTimeMillis());

		ContentResolver resolver = mContext.getContentResolver();

		Uri newUri = resolver.insert(mUri, newValues);
		if (newUri != null) {
			Cursor cursor = resolver.query(newUri, new String[] { MediaStore.Audio.Playlists._ID },
					null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
				playlist.setId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists._ID)));
				cursor.close();
				return true;
			}
		}
		return false;
	}

	@Override
	public Playlist load(long id) {
		String selectionClause = MediaStore.Audio.Playlists._ID + "=?";
		String[] selectionArgs = { String.valueOf(id) };
		String sortOrder = null;

		Cursor cursor = mContext.getContentResolver().query(mUri, PROJECTION_PLAYLIST,
				selectionClause, selectionArgs, sortOrder);

		if (cursor == null) {
			Log.v(TAG, "ERROR querying playlist");
		} else if (cursor.getCount() > 0) {
			Log.v(TAG, "Loading playlist");
			int idIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists._ID);
			int nameIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists.NAME);
			cursor.moveToFirst();
			Playlist playlist = new Playlist(cursor.getLong(idIndex), cursor.getString(nameIndex));
			Log.d(TAG, "Loaded: "+playlist.getId());

			Log.d(TAG, "Getting Playlist items");
			playlist.addReplaceAll( mLibrary.playlistItems.fetch(playlist) );

			return playlist;
		} else {
			Log.v(TAG, "Could not query playlist");
		}
		return null;
	}

	@Override
	public boolean save(Playlist playlist) {
		ContentValues updateValues = new ContentValues();
		String selectionClause = MediaStore.Audio.Playlists._ID + "=?";
		String[] selectionArgs = { String.valueOf(playlist.getId()) };
		int rowsUpdated = 0;
		updateValues.put(MediaStore.Audio.Playlists.NAME, playlist.getName());

		rowsUpdated = mContext.getContentResolver().update(mUri, updateValues, selectionClause,
				selectionArgs);

		if (rowsUpdated > 0) {
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<Playlist> fetchAll() {

		ArrayList<Playlist> playlists = new ArrayList<Playlist>();

		String mSelectionClause = null;
		String[] mSelectionArgs = null;
		String mSortOrder = null;

		Cursor cursor = mContext.getContentResolver().query(mUri, PROJECTION_PLAYLIST,
				mSelectionClause, mSelectionArgs, mSortOrder);

		if (cursor != null) {
			int idIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists._ID);
			int nameIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists.NAME);
			while (cursor.moveToNext()) {
				playlists.add(new Playlist(cursor.getLong(idIndex), cursor.getString(nameIndex)));
			}
		} else {
			Log.v(TAG, "Could not query all playlists");
		}
		return playlists;
	}

	@Override
	public boolean delete(Playlist playlist) {
		String selection = MediaStore.Audio.Playlists._ID+"=?";
		String selectionArgs[] = { String.valueOf( playlist.getId() ) };
		int rows = mContext.getContentResolver().delete( mUri, selection, selectionArgs );
		if ( rows > 0 ) {
			return true;
		}
		return false;
	}

}
