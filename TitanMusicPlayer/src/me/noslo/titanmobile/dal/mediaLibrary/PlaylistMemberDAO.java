package me.noslo.titanmobile.dal.mediaLibrary;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.MediaLibraryItem;
import me.noslo.titanmobile.bll.Playlist;
import me.noslo.titanmobile.bll.PlaylistItem;
import me.noslo.titanmobile.bll.Song;

public interface PlaylistMemberDAO {

	public PlaylistItem addTo(Playlist playlist, Song song);
	
	public boolean removeFrom( Playlist playlist, PlaylistItem playlistItem );

	public ArrayList<PlaylistItem> fetch(Playlist playlist);

	public void populate(Playlist playlist);

}
