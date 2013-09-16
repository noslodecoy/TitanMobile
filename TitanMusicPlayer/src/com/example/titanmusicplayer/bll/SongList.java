package com.example.titanmusicplayer.bll;

import java.util.*;

import android.content.Context;

import com.example.titanmusicplayer.dal.SongDAO;

public class SongList {

	ArrayList<Song> songs;
	private String label;
	private long id;

	public SongList() {
		this.songs = new ArrayList<Song>();
	}

	public SongList(String label) {
		this.songs = new ArrayList<Song>();
		this.label = label;
	}

	public String getName() {
		return this.label;
	}

	public void setName(String label) {
		this.label = label;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public ArrayList<Song> getSongs() {
		return (ArrayList<Song>) this.songs;
	}

	public void empty() {
	}

	public Song get(int i) {
		return this.songs.get(i);
	}

	public ArrayList<Song> getAll() {
		return this.getSongs();
	}

	public int size() {
		return this.songs.size();
	}

	public void add(Song song) {
		this.songs.add(song);
	}

	public void add(int i, Song song) {
		this.songs.add(i, song);
	}

	public void addAll(List<Song> list) {
		for (Song song : list) {
			this.add(song);
		}
	}

	public boolean contains(Song song) {
		return this.songs.contains(song);
	}

	public void remove(Song song) {
		this.songs.remove(song);
	}

	public void remove(int i) {
		remove(this.songs.get(i));
	}
	
	public void fetchAll( Context dataSource ) {
		SongDAO songDAO = new SongDAO();
		this.addAll(songDAO.getSongsFromResource(dataSource));
	}

}
