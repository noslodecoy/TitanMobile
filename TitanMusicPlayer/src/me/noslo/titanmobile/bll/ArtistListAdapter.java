package me.noslo.titanmobile.bll;

import java.util.ArrayList;
import android.content.Context;
import android.widget.ArrayAdapter;

public class ArtistListAdapter extends ArrayAdapter<Artist> {

	ArrayList<Artist> artists;

	public ArtistListAdapter(Context context, int layoutResId,
			int textViewResourceId, ArrayList<Artist> artists) {
		super(context, layoutResId, textViewResourceId, artists);
	}

}