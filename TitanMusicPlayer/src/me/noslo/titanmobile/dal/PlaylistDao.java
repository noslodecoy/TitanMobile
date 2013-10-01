package me.noslo.titanmobile.dal;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.Playlist;
import me.noslo.titanmobile.bll.PlaylistItem;
import me.noslo.titanmobile.bll.Song;

public interface PlaylistDao {

	public boolean create(Playlist playlist);

	public Playlist load(long id);

	public ArrayList<Playlist> fetchAll();

	public boolean save(Playlist playlist);

	public boolean delete(Playlist playlist);

	public boolean addMember(Playlist playlist, Song mSong);
	
	public boolean removeMember(Playlist playlist, PlaylistItem playlistItem);

}
