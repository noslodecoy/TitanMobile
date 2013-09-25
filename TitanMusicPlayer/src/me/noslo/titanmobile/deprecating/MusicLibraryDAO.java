package me.noslo.titanmobile.deprecating;

import me.noslo.titanmobile.R;
import me.noslo.titanmobile.bll.Album;
import me.noslo.titanmobile.bll.Artist;
import me.noslo.titanmobile.bll.Song;
import me.noslo.titanmobile.bll.User;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MusicLibraryDAO {

	static public Context context;
	static private boolean hasFetchedLibrary = false;
	static private SQLiteDatabase db;
	

	public static boolean login(String username, String password) {
		Resources resources = context.getResources();
		TypedArray credentials = resources.obtainTypedArray(R.array.credentials);

		for (int i = 0; i < credentials.length(); i++) {
			String credential = credentials.getString(i);
			String[] pieces = credential.split(":");
			if (pieces[0].equals(username)) {
				if (pieces[1].equals(password)) {
					credentials.recycle();
					return true;
				}
				return false;
			}
		}
		credentials.recycle();
		return false;
	}

	public static SQLiteDatabase getDb() {
		if (db == null) {
			MediaLibraryDatabaseHelper dbHelper = new MediaLibraryDatabaseHelper(context);
			db = dbHelper.getWritableDatabase();
		}
		return db;
	}
	


}