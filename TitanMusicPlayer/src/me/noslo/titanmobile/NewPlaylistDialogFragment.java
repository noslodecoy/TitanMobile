package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.Playlist;
import me.noslo.titanmobile.bll.Song;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

public class NewPlaylistDialogFragment extends DialogFragment {

	public static final String EXTRA_SONG = "me.noslo.titanmobile.extra.SONG";

	private Song mSong;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();

		mSong = new Song(getArguments().getLong("song_id"));

		builder.setView(inflater.inflate(R.layout.dialog_new_playlist, null))
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						EditText newPlaylistTextBox = (EditText) NewPlaylistDialogFragment.this
								.getDialog().findViewById(R.id.new_playlist);
						String playlistName = newPlaylistTextBox.getText().toString();

						Playlist playlist = new Playlist(playlistName);

						TitanPlayerApplication app = (TitanPlayerApplication) NewPlaylistDialogFragment.this
								.getActivity().getApplication();

						app.library.playlists.create(playlist);
						app.library.playlistItems.addTo(playlist, mSong);

					}
				})
				.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						NewPlaylistDialogFragment.this.getDialog().cancel();
					}
				});
		return builder.create();
	}

}