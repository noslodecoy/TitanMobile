package me.noslo.titanmobile.bll;

public class Artist extends SongList {
	
	private static int uniqueId = 0;
	
	public Artist(String artist) {
		super(artist);
		this.setId( ++uniqueId );
	}

	@Override
	public boolean equals(Object object) {
		return (object != null && object instanceof Artist && (this.toString() == object
				.toString()));
	}

}
