package me.noslo.titanmobile.bll;

import java.util.ArrayList;

public class Artist extends SongList {

	private static int uniqueId = 0;
	private ArrayList<Album> albums = new ArrayList<Album>();

	public Artist(String artist) {
		super(artist);
		this.setId(++uniqueId);
	}

	public Artist() {
		super();
		this.setId(++uniqueId);
	}

	@Override
	public boolean equals(Object object) {
		return (object != null && object instanceof Artist && (this.toString() == object
				.toString()));
	}

	public void addAlbum(Album album) {
		if (!albums.contains(album)) {
			this.albums.add(album);
		}
	}

	public ArrayList<Album> getAlbums() {
		return albums;
	}

}
