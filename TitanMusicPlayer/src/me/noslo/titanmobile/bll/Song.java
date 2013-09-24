package me.noslo.titanmobile.bll;

import android.net.Uri;

public class Song implements MediaLibraryObject {

	private long id;
	private String artistName;
	private String albumName;
	private int trackNumber;
	private String name;
	private String fileName;

	public Song() {
	}

	public Song(long songId, String artistName, String albumName, int songTrackNumber, String songName, String fileName) {
		setId(songId);
		setAlbumName(albumName);
		setTrackNumber(songTrackNumber);
		setName(songName);
		setFileName(fileName);
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public void setTrackNumber(int trackNumber) {
		this.trackNumber = trackNumber;
	}

	public void setName(String songName) {
		this.name = songName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getId() {
		return this.id;
	}

	public String getArtistName() {
		return this.artistName;
	}

	public String getAlbumName() {
		return this.albumName;
	}

	public int getTrackNumber() {
		return this.trackNumber;
	}

	public String getName() {
		return this.name;
	}

	public String getArtistAlbum() {
		return this.getAlbumName() + " - " + this.getAlbumName();
	}

	public String getFileName() {
		return this.fileName;
	}

	public Uri getFileUri() {
		return Uri.parse(this.fileName);
	}

}