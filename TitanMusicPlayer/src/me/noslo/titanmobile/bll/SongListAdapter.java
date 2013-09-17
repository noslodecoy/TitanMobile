package me.noslo.titanmobile.bll;

import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//SongArrayAdapter
public class SongListAdapter extends ArrayAdapter<Song> {

	private ArrayList<Song> songs;

	public SongListAdapter(Context context, int layoutResId, int textViewResourceId, ArrayList<Song> songs) {
		super(context, layoutResId, textViewResourceId, songs);
		this.songs = songs;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		TextView text1 = (TextView) view.findViewById(android.R.id.text1);
		TextView text2 = (TextView) view.findViewById(android.R.id.text2);

		Song song = songs.get(position);

		text1.setText(song.getTitle());
		text2.setText(song.getArtistAlbum());
		return view;
	}
}