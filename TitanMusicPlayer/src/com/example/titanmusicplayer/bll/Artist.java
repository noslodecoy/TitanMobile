package com.example.titanmusicplayer.bll;

public class Artist extends SongList {

	public Artist(String artist) {
		super(artist);
	}

	@Override
	public boolean equals(Object object) {
		return (object != null && object instanceof Artist && (this.toString() == object
				.toString()));
	}

}
