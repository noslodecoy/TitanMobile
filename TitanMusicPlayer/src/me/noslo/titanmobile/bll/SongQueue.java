package me.noslo.titanmobile.bll;

import android.util.Log;
import me.noslo.titanmobile.dal.MusicLibraryDAO;

public class SongQueue extends SongList {
	public void addNew( Song song ) {
		super.add( song );
		//int id = (int)MusicLibraryDAO.addQueueItem(song);
		//song.setId(id);
	}
	
	public void remove( Song song ) {
		MusicLibraryDAO.removeQueueItem(song);
	}
}
