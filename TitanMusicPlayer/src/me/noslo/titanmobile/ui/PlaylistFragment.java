package me.noslo.titanmobile.ui;

import me.noslo.titanmobile.R;
import me.noslo.titanmobile.TitanApp;
import me.noslo.titanmobile.bll.Playlist;
import me.noslo.titanmobile.bll.PlaylistItem;
import me.noslo.titanmobile.dal.PlaylistDao;
import me.noslo.titanmobile.dal.PlaylistMemberDao;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Bundle;

import android.app.ListFragment;

public class PlaylistFragment extends ListFragment implements OnItemClickListener {

	private MediaLibraryAdapter mAdapter;
	private Playlist mPlaylist;
	private TitanApp mApp;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	@Override
	public void onResume() {
		super.onResume();
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.song_queue_item, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.remove_queue_item:
			removeQueueItem((PlaylistItem) mAdapter.getItem(info.position));
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void init() {
		mApp = (TitanApp) getActivity().getApplication();
		mPlaylist = getPlaylist();
		mAdapter = new MediaLibraryAdapter(getActivity(), mPlaylist);
		setListAdapter(mAdapter);
		registerForContextMenu(this.getListView());
	}

	private Playlist getPlaylist() {
		long playlistId = getActivity().getIntent().getLongExtra(
				BrowsePlaylistActivity.EXTRA_PLAYLIST, 0);
		if (playlistId > 0 && playlistId != mApp.mediaPlayer.getQueue().getId()) {
			PlaylistDao playlistDao = TitanApp.libraryDao.newPlaylistDao();
			return playlistDao.load(playlistId);
		}
		return mApp.mediaPlayer.getQueue();
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}

	protected void removeQueueItem(PlaylistItem song) {
		mPlaylist.remove(song);
		PlaylistMemberDao playlistMemberDao = TitanApp.libraryDao.newPlaylistMemberDao();
		playlistMemberDao.removeFrom(mApp.mediaPlayer.getQueue(), song);
		
		mAdapter.notifyDataSetChanged();
	}

}