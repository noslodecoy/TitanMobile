package me.noslo.titanmobile.bll;

import java.util.ArrayList;

import android.util.Log;
import me.noslo.titanmobile.dal.MusicLibraryDAO;

public class MediaLibrary {

	private User user;
	private ArrayList<Artist> artists;
	private ArrayList<Album> albums;
	private ArrayList<Song> songs;
	
	MediaLibrary(User user) {
		this.user = user;
		this.artists = new ArrayList<Artist>();
		this.albums = new ArrayList<Album>();
		this.songs = new ArrayList<Song>();
	}
	
	public User getUser() {
		return this.user;
	}
	
	@Override
	public String toString() {
		return "MusicLibrary";
	}

	public void sync() {
		this.artists = new ArrayList<Artist>();
		this.albums = new ArrayList<Album>();
		this.songs = new ArrayList<Song>();
		MusicLibraryDAO.fetchArrayList(user);
	}

	public void addSong(Song song) {
		this.songs.add(song);
	}

	public ArrayList<Song> getSongs() {
		return this.songs;
	}
	
	public ArrayList<Album> getAlbums() {
		return this.albums;
	}

	public ArrayList<Artist> getArtists() {
		return this.artists;
	}
	
	public Album getAlbum( int albumId ) {
		for (Album album : this.albums ) {
			if ( album.getId() == albumId ) {
				return album;
			}
		}
		return new Album();
	}
	
	public Artist getArtist( int artistId ) {
		for (Artist artist : this.artists ) {
			if ( artist.getId() == artistId ) {
				return artist;
			}
		}
		return new Artist();
	}

	public Artist addArtist(String artistString) {
		Artist artist = new Artist(artistString);
		if (!artists.contains(artist)) {
			Log.d( "Artist Not Found", artistString );
			this.artists.add(artist);
			return artist;
		}
		return artists.get(artists.indexOf(artist));
	}

	public Album addAlbum(Artist artist, String albumString) {
		Album album = new Album(artist, albumString);
		if (!albums.contains(album)) {
			this.albums.add(album);
			return album;
		}
		return albums.get(albums.indexOf(album));
	}

	public Song addSong(int id, Album album, int trackNumber, String titleString, String filename) {
		Song song = new Song(id, album, trackNumber, titleString, filename);
		this.songs.add(song);
		return song;
	}

}
