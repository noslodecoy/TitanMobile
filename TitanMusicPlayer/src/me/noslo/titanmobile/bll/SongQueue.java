package me.noslo.titanmobile.bll;

import android.util.Log;
import me.noslo.titanmobile.dal.MusicLibraryDAO;

public class SongQueue extends SongList {
	public void addNew( Song song ) {
		super.add( song );
		Log.d( "SongQueue", "Add Queue Item" );
		int id = (int)MusicLibraryDAO.addQueueItem(song);
		song.setId(id);
	}
	
	public void remove( Song song ) {
		MusicLibraryDAO.removeQueueItem(song);
	}
}
