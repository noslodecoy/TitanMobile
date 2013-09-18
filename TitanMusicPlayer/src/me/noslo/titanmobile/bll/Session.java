package me.noslo.titanmobile.bll;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Session {
	static public User user;
	static public String sessionKey;

	static public void newSession() {
		user = new User();
	}

	static public void login(String username, String sessionKey) {
		Session.sessionKey = sessionKey;
		user = new User();
		user.setUserName(username);
		Session.sessionKey = sessionKey;
	}

	public static List<Song> getStoredQueue() {

		ArrayList<Song> queue = new ArrayList<Song>();
		Random randomGenerator = new Random();
		for (int i = 0; i < 8; i++) {
			int index = randomGenerator.nextInt(Session.user.library.getSongs()
					.size());
			queue.add(Session.user.library.getSongs().get(index));
		}

		return queue;
	}
}
