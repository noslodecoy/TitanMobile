package me.noslo.titanmobile.bll;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

	public static List<Song> getStoredQueue() {

		ArrayList<Song> queue = new ArrayList<Song>();
		Random randomGenerator = new Random();
		for (int i = 0; i < 8; i++) {
//			int songCount = TitanMobile.user.library.getSongs().size();
//			int index = randomGenerator.nextInt( songCount );
//			queue.add(TitanMobile.user.library.getSongs().get(index));
		}

		return queue;
	}
}
