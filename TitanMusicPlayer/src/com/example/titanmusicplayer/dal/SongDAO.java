package com.example.titanmusicplayer.dal;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.example.titanmusicplayer.R;
import com.example.titanmusicplayer.bll.Song;

public class SongDAO {

	public ArrayList<Song> getSongsFromResource( Context context ) {
		ArrayList<Song> songs = new ArrayList<Song>();
		
		Resources resources = context.getResources();
	    TypedArray artists = resources.obtainTypedArray(R.array.song_artists);
	    TypedArray albums = resources.obtainTypedArray(R.array.song_albums);
	    TypedArray tracks = resources.obtainTypedArray(R.array.song_tracks);
	    TypedArray titles = resources.obtainTypedArray(R.array.song_titles);

	    for ( int i = 0; i < titles.length(); i++ ) {
		    Song song = new Song( artists.getString(i), albums.getString(i), tracks.getInt(i, 0), tracks.getString(i));
		    songs.add( song );
	    }
	    
	    artists.recycle();
	    albums.recycle();
	    tracks.recycle();
	    titles.recycle();

	    return songs;
	}
	
}
