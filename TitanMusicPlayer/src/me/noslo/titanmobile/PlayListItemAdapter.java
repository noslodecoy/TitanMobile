package me.noslo.titanmobile;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.PlaylistItem;
import me.noslo.titanmobile.bll.Song;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//SongArrayAdapter
public class PlayListItemAdapter extends ArrayAdapter<PlaylistItem> {

	private int mLayoutResId;
	private Context mContext;
	private ArrayList<PlaylistItem> mSongs;

	public PlayListItemAdapter(Context context, int layoutResId, ArrayList<PlaylistItem> arrayList) {
		super(context, layoutResId, arrayList);
		this.mLayoutResId = layoutResId;
		this.mContext = context;
		this.mSongs = arrayList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
		View row = inflater.inflate(this.mLayoutResId, parent, false);

		Song song = mSongs.get(position);

		TextView text1 = (TextView) row.findViewById(R.id.song_title);
		TextView text2 = (TextView) row.findViewById(R.id.song_meta);
		text1.setText(song.getName());
		text2.setText(song.getArtistAlbum());

		return row;
	}
}