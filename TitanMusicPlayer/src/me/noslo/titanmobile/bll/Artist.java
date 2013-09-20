package me.noslo.titanmobile.bll;

import java.util.ArrayList;

public class Artist extends SongList {

	private static int uniqueId = 0;
	private ArrayList<Album> albums = new ArrayList<Album>();

	public Artist(String name) {
		super(name);
		this.setId(++uniqueId);
	}

	public Artist() {
		super();
		this.setId(++uniqueId);
	}

	@Override
	public boolean equals(Object object) {
		return (object != null && object instanceof Artist && this.toString()
				.equals(object.toString()));
	}

	public void addAlbum(Album album) {
		if (!albums.contains(album)) {
			this.albums.add(album);
		}
	}

	@Override
	public void setName(String name) {
		if (name == null || name == "") {
			name = "Unknown";
		}
		super.setName(name);
	}

	public ArrayList<Album> getAlbums() {
		return albums;
	}

}
