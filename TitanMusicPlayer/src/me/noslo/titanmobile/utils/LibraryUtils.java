package me.noslo.titanmobile.utils;

import me.noslo.titanmobile.TitanApp;
import me.noslo.titanmobile.bll.Playlist;
import me.noslo.titanmobile.dal.PlaylistDao;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class LibraryUtils {
    private static final String TAG = "LibraryUtils";

	public static Playlist getQueue( Application app ) {
		Playlist queue;
		SharedPreferences settings = app.getSharedPreferences(TitanApp.SHARED_PREFS, 0);
		Long queueId = settings.getLong("queue", 0);
		
		PlaylistDao playlistDao = TitanApp.libraryDao.newPlaylistDao(); 
		
		if (queueId > 0) {
			Log.d( TAG, "loading playlist: "+queueId);
			queue = playlistDao.load(queueId);
		} else {
			Log.d( TAG, "creating playlist");
			queue = new Playlist("TitanPlayer Queue");
			playlistDao.create(queue);
			Editor editor = settings.edit();
			editor.putLong("queue", queue.getId());
			editor.commit();
		}
		Log.d( TAG, "loaded playlist: "+queue.getId());
		
		return queue;
	}
	
}
