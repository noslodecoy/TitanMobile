package me.noslo.titanmobile.bll;

public class Artist implements MediaLibraryObject {

	private long id;
	private String name;

	public Artist() {
	}
	
	public Artist(long artistId, String artistName) {
		id = artistId;
		name = artistName;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}