package me.noslo.titanmobile.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MediaLibraryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "titanplayer.db";
	
    private static final int DATABASE_VERSION = 5;
    private static final String LIBRARY_TABLE_CREATE =
                "CREATE TABLE " + LibraryColumns.TABLE_NAME + " ("
                        + LibraryColumns._ID + " INTEGER PRIMARY KEY,"
                        + LibraryColumns.COLUMN_TITLE + " TEXT,"
                        + LibraryColumns.COLUMN_ARTIST + " TEXT,"
                        + LibraryColumns.COLUMN_ALBUM + " TEXT,"
                        + LibraryColumns.COLUMN_TRACK + " INTEGER,"
                        + LibraryColumns.COLUMN_FILE_NAME + " TEXT UNIQUE"
                        + ");";
    
    private static final String QUEUE_TABLE_CREATE =
            "CREATE TABLE " + QueueColumns.TABLE_NAME + " ("
                    + QueueColumns._ID + " INTEGER PRIMARY KEY,"
                    + QueueColumns.COLUMN_SONG_ID + " INTEGER,"
                    + QueueColumns.COLUMN_WEIGHT + " INTEGER"
                    + ");";

    MediaLibraryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LIBRARY_TABLE_CREATE);
        db.execSQL(QUEUE_TABLE_CREATE);
    }
    
    
    /**
    *
    * Demonstrates that the provider must consider what happens when the
    * underlying datastore is changed. In this sample, the database is upgraded the database
    * by destroying the existing data.
    * A real application should upgrade the database in place.
    */
   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       // Logs that the database is being upgraded
       Log.w("TAG", "Upgrading database from version " + oldVersion + " to "
               + newVersion + ", which will destroy all old data");

       // Kills the table and existing data
       db.execSQL("DROP TABLE IF EXISTS " + LibraryColumns.TABLE_NAME);
       // Recreates the database with a new version
       onCreate(db);
   }
    
}