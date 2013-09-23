package me.noslo.titanmobile.bll;

public class Artist implements MediaLibraryObject {

	private int id;
	private String name;

	public Artist(int artistId, String artistName) {
		id = artistId;
		name = artistName;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

}
