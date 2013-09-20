package me.noslo.titanmobile.dal;

import me.noslo.titanmobile.bll.Album;
import me.noslo.titanmobile.bll.Artist;
import me.noslo.titanmobile.bll.MediaScanner;
import me.noslo.titanmobile.bll.Song;
import me.noslo.titanmobile.bll.User;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import com.example.titanmusicplayer.R;

public class MusicLibraryDAO {

	static public Context context;

	static public void fetchArrayList(User user) {

//		Resources resources = context.getResources();
//		TypedArray artistsTypedArray = resources.obtainTypedArray(R.array.song_artists);
//		TypedArray albumsTypedArray = resources.obtainTypedArray(R.array.song_albums);
//		TypedArray tracksTypedArray = resources.obtainTypedArray(R.array.song_tracks);
//		TypedArray titlesTypedArray = resources.obtainTypedArray(R.array.song_titles);
//
//		for (int i = 0; i < titlesTypedArray.length(); i++) {
//			String artistName = artistsTypedArray.getString(i);
//			String albumName = albumsTypedArray.getString(i);
//			int trackNumber = tracksTypedArray.getInt(i, 0);
//			String title = titlesTypedArray.getString(i);
//
//			Artist artist = user.library.addArtist(artistName);
//			Album album = user.library.addAlbum(artist, albumName);
//			Song song = user.library.addSong(i, album, trackNumber, title, "");
//			artist.addAlbum( album );
//			album.add( song );
//		}
		
		MediaScanner scanner = new MediaScanner();
		scanner.scan(user);

//		artistsTypedArray.recycle();
//		albumsTypedArray.recycle();
//		tracksTypedArray.recycle();
//		titlesTypedArray.recycle();
	}

	public static boolean login(String username, String password) {
		Resources resources = context.getResources();
		TypedArray credentials = resources.obtainTypedArray(R.array.credentials);

		for (int i = 0; i < credentials.length(); i++) {
			String credential = credentials.getString(i);
			String[] pieces = credential.split(":");
			if (pieces[0].equals(username)) {
				if (pieces[1].equals(password)) {
					credentials.recycle();
					return true;
				}
				return false;
			}
		}
		credentials.recycle();
		return false;
	}

}