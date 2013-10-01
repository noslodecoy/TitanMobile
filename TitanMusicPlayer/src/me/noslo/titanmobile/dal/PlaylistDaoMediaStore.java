package me.noslo.titanmobile.dal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import me.noslo.titanmobile.bll.Playlist;
import me.noslo.titanmobile.bll.PlaylistItem;
import me.noslo.titanmobile.bll.Song;

public class PlaylistDaoMediaStore implements PlaylistDao {

	private Context mContext;
	private static Map<Long, Playlist> mLoaded = new HashMap<Long, Playlist>();

	private static final String[] PROJECTION_PLAYLIST = { MediaStore.Audio.Playlists._ID,
			MediaStore.Audio.Playlists.NAME, MediaStore.Audio.Playlists.DATE_ADDED,
			MediaStore.Audio.Playlists.DATE_MODIFIED };

	private static final String TAG = "MediaStorePlaylistDAO";

	private Uri mUri;

	public PlaylistDaoMediaStore(MediaLibraryDaoFactory library, Context context) {
		mContext = context;
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

				mLoaded.put(playlist.getId(), playlist);

				cursor.close();
				return true;
			}
		}

		return false;
	}

	@Override
	public Playlist load(long id) {

		if (mLoaded.containsKey(id)) {
			return mLoaded.get(id);
		}

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
			Log.d(TAG, "Loaded: " + playlist.getId());
			cursor.close();

			PlaylistMemberDao playlistMemberDao = new PlaylistMemberDaoMediaStore(mContext);
			playlist.addAll( playlistMemberDao.fetch(playlist) );

			return playlist;
		} else {
			Log.v(TAG, "Could not query playlist");
		}
		cursor.close();

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

				Playlist playlist = new Playlist(cursor.getLong(idIndex),
						cursor.getString(nameIndex));

				if (mLoaded.containsKey(playlist.getId())) {
					playlist = mLoaded.get(playlist.getId());
				} else {
					mLoaded.put(playlist.getId(), playlist);
				}

				playlists.add(playlist);
			}
		} else {
			Log.v(TAG, "Could not query all playlists");
		}
		cursor.close();

		return playlists;
	}

	@Override
	public boolean delete(Playlist playlist) {

		String selection = MediaStore.Audio.Playlists._ID + "=?";
		String selectionArgs[] = { String.valueOf(playlist.getId()) };
		int rows = mContext.getContentResolver().delete(mUri, selection, selectionArgs);
		if (rows > 0) {
			if (mLoaded.containsKey(playlist.getId())) {
				mLoaded.remove(playlist.getId());
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean addMember(Playlist playlist, Song song) {
		PlaylistMemberDao playlistMemberDao = new PlaylistMemberDaoMediaStore(mContext);
		playlistMemberDao.addTo(playlist, song);
		playlist.add(song);
		return true;
	}

	@Override
	public boolean removeMember(Playlist playlist, PlaylistItem playlistItem) {
		PlaylistMemberDao playlistMemberDao = new PlaylistMemberDaoMediaStore(mContext);
		playlistMemberDao.removeFrom(playlist, playlistItem);
		playlist.remove(playlistItem);
		return true;
	}

}
