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

	public void sync(Context context) {
		this.artists = new ArrayList<Artist>();
		this.albums = new ArrayList<Album>();
		this.songs = new ArrayList<Song>();

		ArrayList<ArrayList<String>> list = MusicLibraryDAO.fetchArrayList(
				context, user);

		for (ArrayList<String> listItem : list) {
			int trackNumber = Integer.parseInt(listItem.get(2));
			String titleString = listItem.get(3);

			Artist artist = this.addArtist(listItem.get(0));
			Album album = this.addAlbum(artist, listItem.get(1));
			this.addSong(album, trackNumber, titleString);
		}

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

	public Song addSong(Album album, int trackNumber, String titleString) {
		Song song = new Song(album, trackNumber, titleString);
		this.songs.add(song);
		return song;
	}

}
