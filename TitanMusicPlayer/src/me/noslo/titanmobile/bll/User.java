package me.noslo.titanmobile.bll;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User {

	public MediaLibrary library;
	private String username;
	public SongList queue;

	public User() {
		library = new MediaLibrary(this);
		this.library.sync();
		queue = new SongList();
		getStoredQueue();
	}

	public List<Song> getStoredQueue() {

		ArrayList<Song> queue = new ArrayList<Song>();
		Random randomGenerator = new Random();
		for (int i = 0; i < 8; i++) {
			int songCount = this.library.getSongs().size();
			int index = randomGenerator.nextInt( songCount );
			queue.add(this.library.getSongs().get(index));
		}
		this.queue.replaceAll(queue);
		return queue;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

}
