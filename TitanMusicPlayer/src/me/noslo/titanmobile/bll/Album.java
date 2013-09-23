package me.noslo.titanmobile.bll;

public class Album implements MediaLibraryObject {

	private int id;
	private String name;
	private String artistName;

	public Album(int albumId, String albumName) {
		id = albumId;
		name = albumName;
	}
	
	public Album(int albumId, String albumName, String albumArtistName) {
		id = albumId;
		name = albumName;
		artistName = albumArtistName;
	}

	public String getName() {
		return name;
	}
	
	public String getArtistName() {
		return artistName;
	}
	
	public int getId() {
		return id;
	}

}
