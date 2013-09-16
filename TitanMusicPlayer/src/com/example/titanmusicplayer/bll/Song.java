package com.example.titanmusicplayer.bll;

import java.io.File;
import java.util.Comparator;
import java.util.Locale;

public class Song {

	private boolean isEmpty;

	// song info from database
	private Long id;
	private String title;
	private String artist;
	private String album;
	private int track;
	private int songLength;

	public Song() {
		this.isEmpty = true;
	}

	public Song(File inFile) {
	}

	public Song(String artist, String album, int track, String title) {
		this.isEmpty = false;
		this.artist = artist;
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

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getArtist() {
		return this.artist;
	}

	public String getAlbum() {
		return this.album;
	}

	public void setAlbum(String album) {
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

	public boolean equals(Song songToCompare) {
		if (this == songToCompare) {
			return true;
		}
		return (this.getTitle().equals(songToCompare.getTitle()) && this.getArtist().equals(songToCompare.getArtist()));
	}

	public String getArtistAlbum() {
		return this.getArtist() + " - " + this.getAlbum();
	}

	public boolean isEmpty() {
		return this.isEmpty;
	}

	public static Comparator<Song> CompareByTitle = new Comparator<Song>() {

		@Override
		public int compare(Song song1, Song song2) {
			String songName1 = song1.getTitle().toLowerCase(Locale.getDefault());
			String songName2 = song2.getTitle().toUpperCase(Locale.getDefault());
			return songName1.compareTo(songName2);
		}

	};

	public static Comparator<Song> CompareByArtist = new Comparator<Song>() {
		@Override
		public int compare(Song song1, Song song2) {
			String songName1 = song1.getArtist().toUpperCase(Locale.getDefault());
			String songName2 = song2.getArtist().toUpperCase(Locale.getDefault());
			return songName1.compareTo(songName2);
		}
	};

}
