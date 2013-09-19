package me.noslo.titanmobile;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.Song;

import com.example.titanmusicplayer.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//SongArrayAdapter
public class SongListAdapter extends ArrayAdapter<Song> {

	private int layoutResId;
	private Context context;
	private ArrayList<Song> songs;

	public SongListAdapter(Context context, int layoutResId, ArrayList<Song> songs) {
		super(context, layoutResId, songs);
		this.layoutResId = layoutResId;
		this.context = context;
		this.songs = songs;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View row = inflater.inflate(this.layoutResId, parent, false);
		
		Song song = songs.get(position);

		TextView text1 = (TextView) row.findViewById(R.id.song_title);
		TextView text2 = (TextView) row.findViewById(R.id.song_meta);
		text1.setText(song.getTitle());
		text2.setText(song.getArtistAlbum());

		return row;
	}
}