package com.example.titanmusicplayer.bll;

public class Session {
	static public User user;

	static public void newSession() {
		user = new User();
	}
}
