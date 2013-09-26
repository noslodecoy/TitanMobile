package me.noslo.titanmobile.dal.mediaLibrary;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.Album;
import me.noslo.titanmobile.bll.Artist;
import me.noslo.titanmobile.bll.MediaLibraryItem;

public interface AlbumDAO {

	public Album load(long id);

	public ArrayList<MediaLibraryItem> fetchAll();

	public ArrayList<MediaLibraryItem> fetch(Artist artist);
}
