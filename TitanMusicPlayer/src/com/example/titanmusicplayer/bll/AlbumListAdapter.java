package com.example.titanmusicplayer.bll;

import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//SongArrayAdapter
public class AlbumListAdapter extends ArrayAdapter<Album> {

	private ArrayList<Album> albums;

	public AlbumListAdapter(Context context, int layoutResId,
			int textViewResourceId, ArrayList<Album> albums) {
		super(context, layoutResId, textViewResourceId, albums);
		this.albums = albums;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		TextView text1 = (TextView) view.findViewById(android.R.id.text1);
		TextView text2 = (TextView) view.findViewById(android.R.id.text2);
		Album album = albums.get(position);
		text1.setText(album.toString());
		text2.setText(album.getArtist().toString());
		return view;
	}
}