package me.noslo.titanmobile.bll;

public class User {

	public MusicLibrary library;
	private String username;
	public SongList queue;

	public User() {
		library = new MusicLibrary(this);
		this.library.sync();
		queue = new SongList();
		queue.replaceAll( TitanMobile.getStoredQueue() );
	}

	public String getUsername() {
		return this.username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

}
