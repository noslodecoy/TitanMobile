package me.noslo.titanmobile.dal;

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

public class MediaStorePlaylistDAO implements PlaylistDAO {

	private Context mContext;
	private static final String[] PROJECTION_PLAYLIST_ITEM = { MediaStore.Audio.Media._ID,
			MediaStore.Audio.Playlists.Members.AUDIO_ID, MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.TRACK,
			MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
			MediaStore.Audio.Playlists.Members.PLAY_ORDER };

	private static final String[] INSERT_PROJECTION_PLAYLIST = { MediaStore.Audio.Playlists.NAME,
			MediaStore.Audio.Playlists.DATE_ADDED, MediaStore.Audio.Playlists.DATE_MODIFIED };

	public MediaStorePlaylistDAO(Context context) {
		mContext = context;
	}

	@Override
	public boolean create(Playlist playlist) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Playlist load(Playlist playlist) {
		Cursor cursor = mContext.getContentResolver()
				.query(MediaStore.Audio.Playlists.Members.getContentUri("external", playlist.getId()),
						PROJECTION_PLAYLIST_ITEM, null, null,
						MediaStore.Audio.Playlists.Members.PLAY_ORDER);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			PlaylistItem song = new PlaylistItem(cursor.getLong(0), cursor.getLong(1),
					cursor.getString(2), cursor.getString(3), cursor.getInt(4),
					cursor.getString(5), cursor.getString(6));
			this.add(song);
			cursor.moveToNext();
		}
		cursor.close();
	}

	@Override
	public boolean save(Playlist playlist) {
		ContentResolver resolver = mContext.getContentResolver();
		ContentValues inserts = new ContentValues();
		inserts.put(MediaStore.Audio.Playlists.NAME, playlist.getName());
		inserts.put(MediaStore.Audio.Playlists.DATE_ADDED, System.currentTimeMillis());
		inserts.put(MediaStore.Audio.Playlists.DATE_MODIFIED, System.currentTimeMillis());
		Uri uri = resolver.insert(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, inserts);
		if (uri != null) {
			Cursor cursor = resolver.query(uri, new String[] { MediaStore.Audio.Playlists._ID },
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
	public boolean add(Song song) {
		// TODO Auto-generated method stub
		return false;
	}

}
