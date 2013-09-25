package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.MediaQueuePlayer;
import me.noslo.titanmobile.bll.Playlist;
import me.noslo.titanmobile.bll.User;
import me.noslo.titanmobile.dal.MediaLibraryDAO;
import me.noslo.titanmobile.deprecating.MusicLibraryDAO;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class TitanPlayerApplication extends Application {

	public User user;
	public MediaQueuePlayer mediaPlayer;
	public Playlist queue;
	public MediaLibraryDAO library;
	
	private static final String TAG = "TitanPlayerApplication";

	public static final String SHARED_PREFS = "me.noslo.titanmobile.PREFS";

	public void onCreate() {
		MusicLibraryDAO.context = this;
		library = new MediaLibraryDAO( this );
		getQueue();
		mediaPlayer = new MediaQueuePlayer(this, queue);
	}

	public void getQueue() {
		SharedPreferences settings = getSharedPreferences(TitanPlayerApplication.SHARED_PREFS, 0);
		Long queueId = settings.getLong("queue", 0);
		if (queueId > 0) {
			Log.d( TAG, "loading playlist: "+queueId);
			queue = library.playlists.load(queueId);
		} else {
			Log.d( TAG, "creating playlist");
			queue = new Playlist("TitanPlayer Queue");
			library.playlists.create(queue);
			Editor editor = settings.edit();
			editor.putLong("queue", queue.getId());
			editor.commit();
		}
		Log.d( TAG, "loaded playlist: "+queue.getId());
	}

	public boolean login(String username, String password) {
		if (MusicLibraryDAO.login(username, password)) {
			user = new User();
			user.setUserName(username);
			getQueue();
			return true;
		}
		return false;
	}

}
