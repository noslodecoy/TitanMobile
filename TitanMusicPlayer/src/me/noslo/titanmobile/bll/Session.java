package me.noslo.titanmobile.bll;

public class Session {
	static public User user;

	static public void newSession() {
		user = new User();
	}
}
