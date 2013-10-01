package me.noslo.titanmobile.bll;

public class PlaylistItem extends Song implements MediaLibraryItem {

	private long mSongId;

	public PlaylistItem() {
	}

	public PlaylistItem(Song song) {
		setSongId(song.getId());
		setAlbumName(song.getAlbumName());
		setTrackNumber(song.getTrackNumber());
		setName(song.getName());
		setFileName(song.getFileName());
	}

	public PlaylistItem(long playlistId, long songId, String artistName, String albumName,
			int songTrackNumber, String songName, String fileName) {
		setId(playlistId);
		setSongId(songId);
		setAlbumName(albumName);
		setTrackNumber(songTrackNumber);
		setName(songName);
		setFileName(fileName);
	}

	public void setSongId(long songId) {
		mSongId = songId;
	}

	public long getSongId() {
		return this.mSongId;
	}

}