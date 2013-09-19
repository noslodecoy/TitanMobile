package me.noslo.titanmobile.bll;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

public class TitanMobile {
	static public User user;
	static public String sessionKey;
	static public MediaPlayer mediaPlayer;

	static public void newSession() {
		user = new User();
	}

	static public void login(String username, String sessionKey) {
		TitanMobile.sessionKey = sessionKey;
		user = new User();
		user.setUserName(username);
		TitanMobile.sessionKey = sessionKey;
	}


}
