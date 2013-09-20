package me.noslo.titanmobile.bll;

import java.util.*;

public class SongList {

	ArrayList<Song> songs;
	private String name;
	private int id;

	public SongList() {
		setName("");
		this.songs = new ArrayList<Song>();
	}

	public SongList(String name) {
		setName(name);
		this.songs = new ArrayList<Song>();
	}

	public String getName() {
		return this.toString();
	}

	@Override
	public String toString() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public ArrayList<Song> getSongs() {
		return (ArrayList<Song>) songs;
	}

	public void empty() {
	}

	public Song get(int i) {
		return songs.get(i);
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

	public void replaceAll(List<Song> songs) {
		this.songs = new ArrayList<Song>();
		for (Song song : songs) {
			this.add(song);
		}
	}

}
