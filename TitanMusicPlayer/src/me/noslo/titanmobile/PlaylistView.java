package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.Playlist;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class PlaylistView extends ListView {

	private Playlist mPlaylist;
	private MediaLibraryAdapter mAdapter;
	private Context mContext;

	public PlaylistView(Context context) {
		super(context);
		init(context);
	}

	public PlaylistView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PlaylistView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private Playlist getPlaylist() {
		if (mContext instanceof TitanPlayerActivity) {
			long playlistId = ((TitanPlayerActivity) mContext).getIntent().getLongExtra(
					BrowsePlaylistActivity.EXTRA_PLAYLIST, 0);
			if (playlistId > 0) {
				return ((TitanPlayerActivity) mContext).library.playlists.load(playlistId);
			}

			return ((TitanPlayerActivity) mContext).app.queue;
		}
		return new Playlist();
	}

	private void init(Context context) {

		mContext = context;
		mPlaylist = getPlaylist();

		mAdapter = new MediaLibraryAdapter(this.getContext(), mPlaylist);
		setAdapter(mAdapter);
		setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		((Activity) mContext).registerForContextMenu(this);

	}

}
