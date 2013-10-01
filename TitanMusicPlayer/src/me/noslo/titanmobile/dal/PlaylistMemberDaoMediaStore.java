package me.noslo.titanmobile.dal;

import java.util.ArrayList;

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

public class PlaylistMemberDaoMediaStore implements PlaylistMemberDao {

	private Context mContext;
	private static final String[] PROJECTION_PLAYLIST_ITEM = {
			MediaStore.Audio.Playlists.Members._ID, MediaStore.Audio.Playlists.Members.AUDIO_ID,
			MediaStore.Audio.Playlists.Members.PLAYLIST_ID, MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.TRACK,
			MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
			MediaStore.Audio.Playlists.Members.PLAY_ORDER };

	private static final String TAG = "MediaStorePlaylistMemberDAO";

	public PlaylistMemberDaoMediaStore(Context context) {
		mContext = context;
	}

	@Override
	public PlaylistItem addTo(Playlist playlist, Song song) {
		long nextPlaylistId = getNextPlayorder(playlist);

		Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlist.getId());
		ContentResolver resolver = mContext.getContentResolver();

		ContentValues values = new ContentValues();
		values.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, nextPlaylistId);
		values.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, song.getId());
		Uri newUri = resolver.insert(uri, values);

		if (newUri != null) {
			Log.d(TAG, "added playlist item");
			Cursor cursor = resolver.query(newUri, PROJECTION_PLAYLIST_ITEM, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();

				int idIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists.Members._ID);
				int songIdIndex = cursor
						.getColumnIndex(MediaStore.Audio.Playlists.Members.AUDIO_ID);
				int artistIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.ARTIST);
				int albumIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.ALBUM);
				int trackIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.TRACK);
				int titleIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.TITLE);
				int dataIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.DATA);

				PlaylistItem playlistItem = new PlaylistItem(cursor.getLong(idIndex),
						cursor.getLong(songIdIndex), cursor.getString(artistIndex),
						cursor.getString(albumIndex), cursor.getInt(trackIndex),
						cursor.getString(titleIndex), cursor.getString(dataIndex));

				cursor.close();
				return playlistItem;
			}
		}
		return null;
	}

	private long getNextPlayorder(Playlist playlist) {
		if (playlist == null || playlist.getId() < 1) {
			Log.d(TAG, "invalid playlist");
			return 0;
		}

		Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlist.getId());
		Cursor cursor = mContext.getContentResolver().query(uri, PROJECTION_PLAYLIST_ITEM, null,
				null, MediaStore.Audio.Playlists.Members.PLAY_ORDER);
		if (cursor != null && cursor.moveToLast()) {
			int playOrderIndex = cursor
					.getColumnIndex(MediaStore.Audio.Playlists.Members.PLAY_ORDER);
			long index = cursor.getLong(playOrderIndex);
			cursor.close();
			return index;
		}
		return 1;
	}

	@Override
	public boolean removeFrom(Playlist playlist, PlaylistItem playlistItem) {
		Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlist.getId());

		String selection = MediaStore.Audio.Playlists.Members.PLAYLIST_ID + "=? AND "
				+ MediaStore.Audio.Playlists._ID + "=?";
		String selectionArgs[] = { String.valueOf(playlist.getId()),
				String.valueOf(playlistItem.getId()) };
		int rows = mContext.getContentResolver().delete(uri, selection, selectionArgs);
		if (rows > 0) {
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<PlaylistItem> fetch(Playlist playlist) {
		if (playlist == null || playlist.getId() <= 0) {
			Log.d(TAG, "Invalid playlist passed");
			return new ArrayList<PlaylistItem>();
		}
		String sortOrder = MediaStore.Audio.Playlists.Members.PLAY_ORDER;
		return fetch(playlist, null, null, sortOrder);
	}

	private ArrayList<PlaylistItem> fetch(Playlist playlist, String selectionClause,
			String[] selectionArgs, String sortOrder) {

		ArrayList<PlaylistItem> playlistItems = new ArrayList<PlaylistItem>();

		Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlist.getId());

		Cursor cursor = mContext.getContentResolver().query(uri, PROJECTION_PLAYLIST_ITEM,
				selectionClause, selectionArgs, sortOrder);

		if (cursor != null) {
			Log.v(TAG, "Getting playlist items");

			int idIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists.Members._ID);
			int songIdIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.AUDIO_ID);
			int artistIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.ARTIST);
			int albumIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.ALBUM);
			int trackIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.TRACK);
			int titleIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.TITLE);
			int dataIndex = cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.DATA);

			while (cursor.moveToNext()) {
				PlaylistItem song = new PlaylistItem(cursor.getLong(idIndex),
						cursor.getLong(songIdIndex), cursor.getString(artistIndex),
						cursor.getString(albumIndex), cursor.getInt(trackIndex),
						cursor.getString(titleIndex), cursor.getString(dataIndex));
				playlistItems.add(song);
			}
		} else {
			Log.v(TAG, "Could not fetch playlist items");
		}
		cursor.close();

		return playlistItems;

	}

//	@Override
//	public void populate(Playlist playlist) {
////		playlist.clear();
////		ArrayList<PlaylistItem> items = fetch(playlist);
////		for (PlaylistItem item : items) {
////			playlist.add(item);
////		}
//	}

}
