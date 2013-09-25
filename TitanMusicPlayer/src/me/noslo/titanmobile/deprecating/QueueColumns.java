package me.noslo.titanmobile.deprecating;

import android.provider.BaseColumns;

public final class QueueColumns implements BaseColumns {

	private QueueColumns() {
	}

	public static final String TABLE_NAME = "queue";

	public static final String COLUMN_SONG_ID = "song_id";
	public static final String COLUMN_WEIGHT = "weight";

}