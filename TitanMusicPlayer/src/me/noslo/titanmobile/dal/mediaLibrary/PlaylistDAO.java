package me.noslo.titanmobile.dal.mediaLibrary;

import java.util.ArrayList;
import me.noslo.titanmobile.bll.Playlist;

public interface PlaylistDAO {

	public boolean create(Playlist playlist);

	public Playlist load(long id);

	public ArrayList<Playlist> fetchAll();

	public boolean save(Playlist playlist);

	public boolean delete(Playlist playlist);

}
