package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.MediaPlayer;
import me.noslo.titanmobile.bll.User;
import me.noslo.titanmobile.dal.MusicLibraryDAO;
import android.app.Application;

public class TitanPlayerApplication extends Application {

	public User user;
	public MediaPlayer mediaPlayer;

	public static final String SHARED_PREFS = "me.noslo.titanmobile.PREFS";

	public void onCreate() {
		MusicLibraryDAO.context = this;
	}

	public boolean login(String username, String password) {
		if (MusicLibraryDAO.login(username, password)) {
			user = new User();
			user.setUserName(username);
			mediaPlayer = new MediaPlayer(this, user.queue);
			return true;
		}
		return false;
	}

}
