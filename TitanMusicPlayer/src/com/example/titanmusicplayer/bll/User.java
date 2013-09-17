package com.example.titanmusicplayer.bll;

public class User {

	public MusicLibrary library;

	public User() {
		library = new MusicLibrary(this);
	}

}
