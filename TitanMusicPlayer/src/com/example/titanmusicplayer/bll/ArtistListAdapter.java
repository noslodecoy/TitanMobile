package com.example.titanmusicplayer.bll;

import java.util.ArrayList;
import android.content.Context;
import android.widget.ArrayAdapter;

public class ArtistListAdapter extends ArrayAdapter<Artist> {

	ArrayList<Artist> artists;

	public ArtistListAdapter(Context context, int layoutResId,
			int textViewResourceId, ArrayList<Artist> artists) {
		super(context, layoutResId, textViewResourceId, artists);
	}

//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		View view = super.getView(position, convertView, parent);
////		TextView text1 = (TextView) view.findViewById(android.R.id.text1);
////		Artist artist = artists.get(position);
////		text1.setText(artist.toString());
//		return view;
//	}
}