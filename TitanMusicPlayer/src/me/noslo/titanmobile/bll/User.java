package me.noslo.titanmobile.bll;

public class User {

	public MusicLibrary library;
	private String username;

	public User() {
		library = new MusicLibrary(this);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

}
