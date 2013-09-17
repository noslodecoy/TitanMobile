package com.example.titanmusicplayer.dal;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import com.example.titanmusicplayer.R;
import com.example.titanmusicplayer.bll.User;

public class MusicLibraryDAO {

	static public ArrayList<ArrayList<String>> fetchArrayList(Context context,
			User user) {

		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

		Resources resources = context.getResources();
		TypedArray artistsTypedArray = resources
				.obtainTypedArray(R.array.song_artists);
		TypedArray albumsTypedArray = resources
				.obtainTypedArray(R.array.song_albums);
		TypedArray tracksTypedArray = resources
				.obtainTypedArray(R.array.song_tracks);
		TypedArray titlesTypedArray = resources
				.obtainTypedArray(R.array.song_titles);

		for (int i = 0; i < titlesTypedArray.length(); i++) {

			ArrayList<String> song = new ArrayList<String>();
			song.add(artistsTypedArray.getString(i));
			song.add(albumsTypedArray.getString(i));
			song.add(tracksTypedArray.getString(i));
			song.add(titlesTypedArray.getString(i));

			list.add(song);
		}

		artistsTypedArray.recycle();
		albumsTypedArray.recycle();
		tracksTypedArray.recycle();
		titlesTypedArray.recycle();

		return list;
	}

}