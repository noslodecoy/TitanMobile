package me.noslo.titanmobile.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountDataHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "accounts";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_PASSWORD = "password";

	private static final String DATABASE_NAME = "titanplayer.db";
	private static final int DATABASE_VERSION = 1;

	private static final String TAG = AccountDataHelper.class.getName();

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_NAME + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_USERNAME + " text not null, "
			+ COLUMN_PASSWORD + " text not null);";

	public AccountDataHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

}