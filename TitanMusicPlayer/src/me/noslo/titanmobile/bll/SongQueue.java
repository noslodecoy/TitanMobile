package me.noslo.titanmobile.bll;

import me.noslo.titanmobile.dal.MusicLibraryDAO;

public class SongQueue extends SongList {
	@Override
	public void add( Song song ) {
		super.add( song );
		MusicLibraryDAO.addQueueItem(song);
	}
}
