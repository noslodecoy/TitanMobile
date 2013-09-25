package me.noslo.titanmobile.deprecating;

import android.provider.BaseColumns;

public final class LibraryColumns implements BaseColumns {

	private LibraryColumns() {
	}

	public static final String TABLE_NAME = "library";

	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_ARTIST = "artist";
	public static final String COLUMN_ALBUM = "album";
	public static final String COLUMN_TRACK = "track";
	public static final String COLUMN_FILE_NAME = "file";

}