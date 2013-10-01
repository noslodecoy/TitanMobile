package me.noslo.titanmobile.dal;

import android.content.Context;

public class MediaLibraryDaoFactory {

	private Context mContext;

	public MediaLibraryDaoFactory(Context context) {
		mContext = context;
	}
	
	public PlaylistDao newPlaylistDao() {
		return new PlaylistDaoMediaStore(this, mContext);
	}
	
	public ArtistDao newArtistDao() {
		return new ArtistDaoMediaStore(mContext);	
	}
	
	public AlbumDao newAlbumDao() {
		return new AlbumDaoMediaStore(mContext);
	}
	
	public SongDao newSongDao() {
		return new SongDaoMediaStore(mContext);
	}
	
	public PlaylistMemberDao newPlaylistMemberDao() {
		return new PlaylistMemberDaoMediaStore(mContext);
	}

}