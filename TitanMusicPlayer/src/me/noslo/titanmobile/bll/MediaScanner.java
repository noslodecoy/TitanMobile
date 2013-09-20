package me.noslo.titanmobile.bll;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

import android.media.MediaMetadataRetriever;
import android.util.Log;

public class MediaScanner {
	// SDCard Path
	final String MEDIA_PATH = new String("/sdcard/");
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	private User user;
	private int id;

	// Constructor
	public MediaScanner() {

	}

	public void scan(User user) {
		File home = new File(MEDIA_PATH);
		id = user.library.getSongs().size();
		this.user = user;
		walk(home);
	}

	private void walk(File root) {

		File[] list = root.listFiles();

		for (File file : list) {
			if (file.isDirectory()) {
				walk(file);
			} else if (file.toString().endsWith(".mp3")
					|| file.toString().endsWith(".MP3")) {
				addFile(file);
			}
		}
	}

	private void addFile(File file) {
		try {

			MediaMetadataRetriever mmr = new MediaMetadataRetriever();
			mmr.setDataSource(file.toString());
			String artistName = mmr
					.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
			String albumName = mmr
					.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
			String title = mmr
					.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
			int trackNumber = Integer
					.parseInt(mmr
							.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CD_TRACK_NUMBER));
			Artist artist = user.library.addArtist(artistName);
			Album album = user.library.addAlbum(artist, albumName);
			Song song = user.library.addSong(++id, album, trackNumber, title, file.toString());
			artist.addAlbum(album);
			album.add(song);
		} catch (Exception e) {

		}
	}

}