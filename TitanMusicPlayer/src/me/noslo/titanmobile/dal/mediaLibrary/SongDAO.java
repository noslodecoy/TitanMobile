package me.noslo.titanmobile.dal.mediaLibrary;

import java.util.ArrayList;
import me.noslo.titanmobile.bll.Album;
import me.noslo.titanmobile.bll.Song;

public interface SongDAO {

	public Song load(long id);

	public ArrayList<Song> fetchAll();
	
	public ArrayList<Song> fetch( Album album );

}
