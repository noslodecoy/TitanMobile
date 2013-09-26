package me.noslo.titanmobile;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.MediaLibraryItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//SongArrayAdapter
public class MediaLibraryAdapter extends ArrayAdapter<MediaLibraryItem> {

	private int mLayoutResId;
	private Context mContext;
	private ArrayList<MediaLibraryItem> mItems;

	public MediaLibraryAdapter(Context context, ArrayList<MediaLibraryItem> arrayList) {
		super(context, R.layout.song_list_item, arrayList);
		this.mLayoutResId = R.layout.song_list_item;
		this.mContext = context;
		this.mItems = arrayList;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
		View row = inflater.inflate(this.mLayoutResId, parent, false);

		MediaLibraryItem song = mItems.get(position);

		TextView itemName = (TextView) row.findViewById(R.id.song_title);
		TextView itemMeta = (TextView) row.findViewById(R.id.song_meta);
		itemName.setText(song.getName());
		itemMeta.setText(song.getMeta());

		return row;
	}
}