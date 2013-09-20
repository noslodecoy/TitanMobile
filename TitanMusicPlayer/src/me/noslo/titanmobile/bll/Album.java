package me.noslo.titanmobile.bll;

public class Album extends SongList {

	private Artist artist;
	private static int uniqueId = 0;

	public Album(Artist artist, String name) {
		super(name);
		this.artist = artist;
		this.setId(++uniqueId);
	}

	public Album() {
		super();
		this.artist = new Artist();
		this.setId(++uniqueId);
	}

	public Artist getArtist() {
		return this.artist;
	}

	@Override
	public void setName(String name) {
		if (name == null || name == "") {
			name = "Untitled";
		}
		super.setName(name);
	}

	@Override
	public boolean equals(Object object) {
		return (object != null && object instanceof Album && (this.toString()
				.equals(object.toString())));
	}
}
