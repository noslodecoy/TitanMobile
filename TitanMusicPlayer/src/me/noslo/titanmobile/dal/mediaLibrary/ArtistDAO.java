package me.noslo.titanmobile.dal.mediaLibrary;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.Artist;

public interface ArtistDAO {

	public Artist load(long id);

	public ArrayList<Artist> fetchAll();
	
}
