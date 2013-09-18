package me.noslo.titanmobile.dal;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.Album;
import me.noslo.titanmobile.bll.Artist;
import me.noslo.titanmobile.bll.User;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import com.example.titanmusicplayer.R;

public class MusicLibraryDAO {

	static public Context context;
	
	static public void fetchArrayList(User user) {

		Resources resources = context.getResources();
		TypedArray artistsTypedArray = resources.obtainTypedArray(R.array.song_artists);
		TypedArray albumsTypedArray = resources.obtainTypedArray(R.array.song_albums);
		TypedArray tracksTypedArray = resources.obtainTypedArray(R.array.song_tracks);
		TypedArray titlesTypedArray = resources.obtainTypedArray(R.array.song_titles);

		for (int i = 0; i < titlesTypedArray.length(); i++) {
			String artistName = artistsTypedArray.getString(i);
			String albumName = albumsTypedArray.getString(i);
			int trackNumber = tracksTypedArray.getInt(i, 0);
			String title = titlesTypedArray.getString(i);

			Artist artist = user.library.addArtist(artistName);
			Album album = user.library.addAlbum(artist, albumName);
			user.library.addSong(i, album, trackNumber, title);
		}

		artistsTypedArray.recycle();
		albumsTypedArray.recycle();
		tracksTypedArray.recycle();
		titlesTypedArray.recycle();
	}

}