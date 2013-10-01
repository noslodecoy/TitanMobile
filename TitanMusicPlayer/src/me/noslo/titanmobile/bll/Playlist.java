package me.noslo.titanmobile.bll;

public class Playlist extends BaseMediaList {

	private static final long serialVersionUID = -5221165637894343526L;
	private String mName;
	private long mId;

	// private static final String TAG = "Playlist";

	public Playlist() {
		mId = -1;
		mName = "";
	}

	public Playlist(String name) {
		mId = -1;
		mName = name;
	}

	public Playlist(long id, String name) {
		mId = id;
		mName = name;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
//		if ( mId > 0 ) {
//			populate();
//		}
	}
	
//	private void populate() {
//		super.clear();
//		ArrayList<PlaylistItem> items = MediaLibrary.getPlaylistItems(this);
//		for (PlaylistItem item : items) {
//			super.add(item);
//		}
//	}


//	public void add(Song item) {
//		super.add(item);
//		if (mId > 0) {
//			TitanApp.libraryDao.playlistItems.addTo(this, (Song) item);
//		}
//	}
//
//	public void remove(PlaylistItem song) {
//		super.remove(song);
//		if (mId > 0) {
//			TitanApp.libraryDao.playlistItems.removeFrom(this, song);
//		}
//	}

	@Override
	public String toString() {
		return getName();
	}

//	public void reload() {
//		super.clear();
//		ArrayList<PlaylistItem> items = TitanApp.libraryDao.playlistItems.fetch(this);
//		for (PlaylistItem item : items) {
//			super.add(item);
//		}
//	}
	
//	public void create() {
//		if ( mId <= 0 ) {
//			TitanApp.libraryDao.playlists.create(this);
//		}
//	}
}
