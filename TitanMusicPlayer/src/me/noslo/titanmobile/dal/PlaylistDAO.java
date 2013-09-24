package me.noslo.titanmobile.dal;

import me.noslo.titanmobile.bll.Playlist;
import me.noslo.titanmobile.bll.Song;

public interface PlaylistDAO {

	public boolean add(Song song);
	
	public boolean create(Playlist playlist);

	public Playlist load(Playlist playlist);

	public boolean save(Playlist playlist);

}
