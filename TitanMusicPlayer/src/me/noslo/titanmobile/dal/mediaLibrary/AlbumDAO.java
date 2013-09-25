package me.noslo.titanmobile.dal.mediaLibrary;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.Album;
import me.noslo.titanmobile.bll.Artist;

public interface AlbumDAO {

	public Album load(long id);

	public ArrayList<Album> fetchAll();

	public ArrayList<Album> fetch(Artist artist);
}
