package me.noslo.titanmobile.ui;

import java.util.ArrayList;

import me.noslo.titanmobile.R;
import me.noslo.titanmobile.TitanApp;
import me.noslo.titanmobile.bll.Playlist;
import me.noslo.titanmobile.dal.PlaylistDao;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class BrowsePlaylistsActivity extends TitanPlayerActivity implements OnItemClickListener {

	private ListView mList;
	private ArrayList<Playlist> mPlaylists;
	private PlaylistListAdapter mAdapter;
	private PlaylistDao mPlaylistDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_playlists);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		mPlaylistDao = TitanApp.libraryDao.newPlaylistDao();

		fillList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.browse_library, menu);
		return true;
	}

	private void fillList() {
		
		mPlaylists = mPlaylistDao.fetchAll();

		mList = (ListView) findViewById(R.id.browsePlaylistsListView);
		mAdapter = new PlaylistListAdapter(this, android.R.layout.simple_list_item_2,
				android.R.id.text1, mPlaylists);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(this);
		registerForContextMenu(mList);
	}

	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		 Playlist playlist = mAdapter.getItem(position);
		 Intent intent = new Intent(this, BrowsePlaylistActivity.class);
		 intent.putExtra(BrowsePlaylistActivity.EXTRA_PLAYLIST, playlist.getId());
		 startActivity(intent);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.playlist_list_item, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.delete_playlist:
			deletePlaylist(mAdapter.getItem(info.position));
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	public void deletePlaylist(Playlist playlist) {
		if (playlist.getId() != app.mediaPlayer.getQueue().getId()) {
			if (mPlaylistDao.delete(playlist)) {
				mPlaylists.remove(playlist);
				mAdapter.notifyDataSetChanged();
			}
		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle(getString(R.string.delete_playlist));
			alertDialog.setMessage(getString(R.string.cant_delete_queue));
			alertDialog.show();
		}
	}

	public class PlaylistListAdapter extends ArrayAdapter<Playlist> {

		public PlaylistListAdapter(Context context, int layoutResId, int textViewResourceId,
				ArrayList<Playlist> data) {
			super(context, layoutResId, textViewResourceId, data);
		}

	}

}
