package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.MediaQueuePlayer;
import me.noslo.titanmobile.bll.Playlist;
import me.noslo.titanmobile.bll.User;
import me.noslo.titanmobile.dal.MusicLibraryDAO;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class TitanPlayerApplication extends Application {

	public User user;
	public MediaQueuePlayer mediaPlayer;
	public Playlist queue;

	public static final String SHARED_PREFS = "me.noslo.titanmobile.PREFS";

	public void onCreate() {
		MusicLibraryDAO.context = this;
		getQueue();
	}

	public void getQueue() {
		SharedPreferences settings = getSharedPreferences(TitanPlayerApplication.SHARED_PREFS, 0);
		Long queueId = settings.getLong("queue", 0);
		if (queueId > 0) {
			queue = new Playlist(this, queueId);
			Log.d( "Application", "loaded queue: "+queue.getId() );
		} else {
			queue = new Playlist("TitanPlayer Queue");
			queue.save(this);
			Editor editor = settings.edit();
			editor.putLong("queue", queue.getId());
			Log.d( "Application", "created queue: "+queue.getId() );
			editor.commit();
		}
	}

	public boolean login(String username, String password) {
		if (MusicLibraryDAO.login(username, password)) {
			user = new User();
			user.setUserName(username);
			mediaPlayer = new MediaQueuePlayer(this, user.queue);
			return true;
		}
		return false;
	}

}
