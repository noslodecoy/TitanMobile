package me.noslo.titanmobile.dal;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.Album;
import me.noslo.titanmobile.bll.Song;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class SongDaoMediaStore implements SongDao {

	public static String[] PROJECTION_SONG = new String[] { MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
			MediaStore.Audio.Media.TRACK, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA };

	private static String TAG = "MediaStoreAlbumDAO";
	private Uri mUri;
	private Context mContext;

	public SongDaoMediaStore(Context context) {
		mContext = context;
		mUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	}

	public ArrayList<Song> fetchAll() {
		return fetch(null, null, MediaStore.Audio.Media.TITLE_KEY);
	}

	private ArrayList<Song> fetch(String selectionClause, String[] selectionArgs, String sortOrder) {

		ArrayList<Song> songs = new ArrayList<Song>();

		Cursor cursor = mContext.getContentResolver().query(mUri, PROJECTION_SONG, selectionClause,
				selectionArgs, sortOrder);

		if (cursor != null) {
			int idIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
			int artistIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
			int albumIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
			int trackIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TRACK);
			int titleIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
			int dataIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);

			while (cursor.moveToNext()) {
				Song song = new Song(cursor.getLong(idIndex), cursor.getString(artistIndex),
						cursor.getString(albumIndex), cursor.getInt(trackIndex),
						cursor.getString(titleIndex), cursor.getString(dataIndex));
				songs.add(song);
			}
		} else {
			Log.v(TAG, "Could not fetch songs");
		}
		cursor.close();

		return songs;

	}

	@Override
	public Song load(long id) {
		String selectionClause = MediaStore.Audio.Media._ID + "=?";
		String[] selectionArgs = { String.valueOf(id) };
		String sortOrder = null;
		ArrayList<Song> songs = fetch(selectionClause, selectionArgs, sortOrder);
		if (songs.size() > 0) {
			return songs.get(0);
		}
		return null;
	}

	@Override
	public ArrayList<Song> fetch(Album album) {
		if (album == null || album.getId() <= 0) {
			return fetchAll();
		}
		String selectionClause = MediaStore.Audio.Media.ALBUM_ID + "=?";
		String[] selectionArgs = new String[] { String.valueOf(album.getId()) };
		String sortOrder = MediaStore.Audio.Media.TRACK;

		return fetch(selectionClause, selectionArgs, sortOrder);
	}

}
