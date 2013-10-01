package me.noslo.titanmobile.dal;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.Artist;
import me.noslo.titanmobile.bll.MediaLibraryItem;

public interface ArtistDao {

	public Artist load(long id);

	public ArrayList<MediaLibraryItem> fetchAll();
	
}
