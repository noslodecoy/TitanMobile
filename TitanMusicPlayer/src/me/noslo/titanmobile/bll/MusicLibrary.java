package me.noslo.titanmobile.bll;

import java.util.ArrayList;
import me.noslo.titanmobile.dal.MusicLibraryDAO;
import android.content.Context;

public class MusicLibrary {

	private User user;
	private ArrayList<Artist> artists;
	private ArrayList<Album> albums;
	private ArrayList<Song> songs;
	
	MusicLibrary(User user) {
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
	
	public ArrayList<Song> getSongs( int albumId ) {
		if ( albumId == 0 ) {
			return this.songs;
		}
		ArrayList<Song> songs = new ArrayList<Song>();
		for ( Song song : this.songs ) {
			if ( albumId == song.getAlbum().getId() ) {
				songs.add( song );
			}
		}
		return songs;
	}

	public ArrayList<Album> getAlbums() {
		return this.albums;
	}
	
	public ArrayList<Album> getAlbums( int artistId ) {
		if ( artistId == 0 ) {
			return this.albums;
		}
		ArrayList<Album> albums = new ArrayList<Album>();
		for ( Album album : this.albums ) {
			if ( artistId == album.getArtist().getId() ) {
				albums.add( album );
			}
		}
		return albums;
	}

	public ArrayList<Artist> getArtists() {
		return this.artists;
	}

	public Artist addArtist(String artistString) {
		Artist artist = new Artist(artistString);
		if (!artists.contains(artist)) {
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

	public Song addSong(int id, Album album, int trackNumber, String titleString) {
		Song song = new Song(id, album, trackNumber, titleString);
		this.songs.add(song);
		return song;
	}

}
