package me.noslo.titanmobile;

import java.util.ArrayList;

import me.noslo.titanmobile.BrowsePlaylistsActivity.PlaylistListAdapter;
import me.noslo.titanmobile.bll.Album;
import me.noslo.titanmobile.bll.Playlist;
import me.noslo.titanmobile.bll.Song;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class SelectPlaylistDialogFragment extends DialogFragment implements OnClickListener {
	
	public static final String EXTRA_SONG = "me.noslo.titanmobile.extra.SONG";

	ArrayList<Playlist> mPlaylists;
	private PlaylistListAdapter mAdapter;
	private Song mSong;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		mPlaylists = ((TitanPlayerApplication) this.getActivity()
				.getApplication()).library.playlists.fetchAll();

	    mSong = new Song( getArguments().getLong("song_id") );
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.add_to_playlist);
	
		mAdapter = new PlaylistListAdapter((Context)this.getActivity(), android.R.layout.simple_list_item_2,
				android.R.id.text1, mPlaylists);
		builder.setAdapter( mAdapter, this );
		return builder.create();
	}
	
	public class PlaylistListAdapter extends ArrayAdapter<Playlist> {

		public PlaylistListAdapter(Context context, int layoutResId, int textViewResourceId,
				ArrayList<Playlist> data) {
			super(context, layoutResId, textViewResourceId, data);
		}

	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		Playlist playlist = mAdapter.getItem(which);
		((TitanPlayerActivity)this.getActivity()).library.playlistItems.addTo(playlist, mSong);
	}

}