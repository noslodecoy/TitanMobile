package me.noslo.titanmobile.bll;

import java.io.File;
import java.util.Comparator;
import java.util.Locale;

public class Song {

	private boolean isEmpty;

	// song info from database
	private Long id;
	private String title;
	private Album album;
	private int track;
	private int songLength;

	public Song() {
		this.isEmpty = true;
	}

	public Song(File inFile) {
	}

	public Song(Album album, int track, String title) {
		this.isEmpty = false;
		this.album = album;
		this.track = track;
		this.title = title;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public Album getAlbum() {
		return this.album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public int getTrackNumber() {
		return this.track;
	}

	public void setTrackNumber(int track) {
		this.track = track;
	}

	public String getTitle() {
		return this.title;
	}

	public String toString() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTrackLength() {
		return this.songLength;
	}

	public String getFormatedLength() {
		long time = getTrackLength();
		String seconds = Integer.toString((int) (time % 60));
		String minutes = Integer.toString((int) ((time % 3600) / 60));
		String hours = Integer.toString((int) (time / 3600));
		for (int i = 0; i < 2; i++) {
			if (seconds.length() < 2) {
				seconds = "0" + seconds;
			}
			if (minutes.length() < 2) {
				minutes = "0" + minutes;
			}
			if (hours.length() < 2) {
				hours = "0" + hours;
			}
		}
		return hours + ":" + minutes + ":" + seconds;
	}

	public void setTrackLength(int length) {
		this.songLength = length;
	}

	public boolean equals(Song song) {
		if (this == song) {
			return true;
		}
		return (this.getAlbum().equals(song.getAlbum())
				&& this.getTrackNumber() == song.getTrackNumber() && this
				.getTitle().equals(song.getTitle()));
	}

	public String getArtistAlbum() {
		return this.getAlbum().getArtist().toString() + " - "
				+ this.getAlbum().toString();
	}

	public boolean isEmpty() {
		return this.isEmpty;
	}

	public static Comparator<Song> CompareByTitle = new Comparator<Song>() {

		@Override
		public int compare(Song song1, Song song2) {
			String songName1 = song1.getTitle()
					.toLowerCase(Locale.getDefault());
			String songName2 = song2.getTitle()
					.toUpperCase(Locale.getDefault());
			return songName1.compareTo(songName2);
		}

	};

	public static Comparator<Song> CompareByAlbum = new Comparator<Song>() {
		@Override
		public int compare(Song song1, Song song2) {
			String songName1 = song1.getAlbum().toString()
					.toUpperCase(Locale.getDefault());
			String songName2 = song2.getAlbum().toString()
					.toUpperCase(Locale.getDefault());
			return songName1.compareTo(songName2);
		}
	};

}
