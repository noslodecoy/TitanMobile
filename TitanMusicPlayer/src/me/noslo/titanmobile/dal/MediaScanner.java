package me.noslo.titanmobile.dal;

import java.io.File;

import me.noslo.titanmobile.bll.User;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaMetadataRetriever;
import android.os.Environment;

public class MediaScanner {
	// SDCard Path
	private User user;
	private int id;
	private SQLiteDatabase db;

	// Constructor
	public MediaScanner( SQLiteDatabase db ) {
		this.db = db;
	}

	public void scan(User user) {
		File home = new File(Environment.getExternalStorageDirectory()
				.getPath());
		id = user.library.getSongs().size();
		this.user = user;
		walk(home);
	}

	private void walk(File root) {

		File[] list = root.listFiles();

		for (File file : list) {
			if (file.isDirectory()) {
				walk(file);
			} else if (file.toString().endsWith(".mp3")
					|| file.toString().endsWith(".MP3")) {
				addFile(file);
			}
		}
	}

	private void addFile(File file) {
		try {
			MediaMetadataRetriever mmr = new MediaMetadataRetriever();
			mmr.setDataSource(file.toString());
			String artistName = mmr
					.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
			String albumName = mmr
					.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
			String title = mmr
					.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
			int trackNumber = Integer
					.parseInt(mmr
							.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CD_TRACK_NUMBER));
			
			ContentValues values = new ContentValues();
			values.put(LibraryColumns.COLUMN_ARTIST, artistName);
			values.put(LibraryColumns.COLUMN_ALBUM, albumName);
			values.put(LibraryColumns.COLUMN_TRACK, trackNumber);
			values.put(LibraryColumns.COLUMN_TITLE, title);
			values.put(LibraryColumns.COLUMN_FILE_NAME, file.toString());

			db.insertWithOnConflict(LibraryColumns.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		} catch (Exception e) {

		}
	}

}