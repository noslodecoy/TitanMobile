package me.noslo.titanmobile.bll;

public class Album extends SongList {

	private Artist artist;

	public Album(Artist artist, String label) {
		super(label);
		this.artist = artist;
	}

	public Artist getArtist() {
		return this.artist;
	}

	@Override
	public boolean equals(Object object) {
		return (object != null && object instanceof Album && (this.toString() == object
				.toString()));
	}
}
