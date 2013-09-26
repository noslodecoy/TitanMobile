package me.noslo.titanmobile.bll;

public class Album implements MediaLibraryItem {

	private long id;
	private String name;
	private String artistName;

	public Album() {
	}
	
	public Album(long albumId, String albumName, String albumArtistName) {
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

	public long getId() {
		return id;
	}
	
	public String getMeta() {
		return getArtistName();
	}
	
	@Override
	public String toString() {
		return name;
	}

}