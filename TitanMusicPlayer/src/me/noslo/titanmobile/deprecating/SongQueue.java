package me.noslo.titanmobile.deprecating;

import android.util.Log;
import me.noslo.titanmobile.bll.Song;
import me.noslo.titanmobile.dal.MediaLibraryDAO;

public class SongQueue extends SongList {
	public void addNew( Song song ) {
		super.add( song );
		//int id = (int)MusicLibraryDAO.addQueueItem(song);
		//song.setId(id);
	}
	
//	public void remove( Song song ) {
//		MediaLibraryDAO.removeQueueItem(song);
//	}
}
