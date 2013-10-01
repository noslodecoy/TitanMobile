package me.noslo.titanmobile.ui;

import java.util.ArrayList;

import me.noslo.titanmobile.R;
import me.noslo.titanmobile.TitanApp;
import me.noslo.titanmobile.bll.Playlist;
import me.noslo.titanmobile.bll.Song;
import me.noslo.titanmobile.dal.PlaylistDao;
import me.noslo.titanmobile.dal.PlaylistMemberDao;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class SelectPlaylistDialogFragment extends DialogFragment implements OnClickListener {
	
	public static final String EXTRA_SONG = "me.noslo.titanmobile.extra.SONG";

	private ArrayList<Playlist> mPlaylists;
	private PlaylistListAdapter mAdapter;
	private Song mSong;
	private Playlist mNewPlaylist;
	
	private PlaylistMemberDao mPlaylistMemberDao;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		mNewPlaylist = new Playlist("New Playlist");
		
		PlaylistDao playlistDao = TitanApp.libraryDao.newPlaylistDao();
		mPlaylists = playlistDao.fetchAll();

		mPlaylists.add(0, mNewPlaylist);
		
	    mSong = new Song( getArguments().getLong("song_id") );
	    
	    mPlaylistMemberDao = TitanApp.libraryDao.newPlaylistMemberDao();
		
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
		if ( playlist == mNewPlaylist ) {
			Bundle bundle = new Bundle();
			bundle.putLong( "song_id", mSong.getId() );
	        DialogFragment newDialog = new NewPlaylistDialogFragment();
	        newDialog.setArguments( bundle );
	        newDialog.show(getFragmentManager(), "NoticeDialogFragment");
		} else {
			mPlaylistMemberDao.addTo(playlist, mSong);
		}
	}

}