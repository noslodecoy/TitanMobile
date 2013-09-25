package me.noslo.titanmobile.dal;

import me.noslo.titanmobile.dal.mediaLibrary.AlbumDAO;
import me.noslo.titanmobile.dal.mediaLibrary.ArtistDAO;
import me.noslo.titanmobile.dal.mediaLibrary.PlaylistDAO;
import me.noslo.titanmobile.dal.mediaLibrary.PlaylistMemberDAO;
import me.noslo.titanmobile.dal.mediaLibrary.SongDAO;
import me.noslo.titanmobile.dal.mediaLibrary.mediaStore.MediaStoreAlbumDAO;
import me.noslo.titanmobile.dal.mediaLibrary.mediaStore.MediaStoreArtistDAO;
import me.noslo.titanmobile.dal.mediaLibrary.mediaStore.MediaStorePlaylistDAO;
import me.noslo.titanmobile.dal.mediaLibrary.mediaStore.MediaStorePlaylistMemberDAO;
import me.noslo.titanmobile.dal.mediaLibrary.mediaStore.MediaStoreSongDAO;
import android.content.Context;

public class MediaLibraryDAO {

	private Context mContext;
	public PlaylistDAO playlists;
	public ArtistDAO artists;
	public AlbumDAO albums;
	public SongDAO songs;
	public PlaylistMemberDAO playlistItems;

	public MediaLibraryDAO(Context context) {
		mContext = context;
		playlists = new MediaStorePlaylistDAO(this, mContext);
		artists = new MediaStoreArtistDAO(mContext);
		albums = new MediaStoreAlbumDAO(mContext);
		songs = new MediaStoreSongDAO(mContext);
		playlistItems = new MediaStorePlaylistMemberDAO(mContext);
	}

}