package me.noslo.titanmobile;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.Playlist;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
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
	PlaylistListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_playlists);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		fillList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.browse_library, menu);
		return true;
	}

	private void fillList() {
		mPlaylists = library.playlists.fetchAll();

		mList = (ListView) findViewById(R.id.browsePlaylistsListView);
		mAdapter = new PlaylistListAdapter(this, android.R.layout.simple_list_item_2,
				android.R.id.text1, mPlaylists);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(this);
		registerForContextMenu(mList);
	}

	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		// Playlist playlist = mAdapter.getItem(position);
		// Intent intent = new Intent(this, BrowsePlaylistActivity.class);
		// intent.putExtra(BrowseLibraryActivity.EXTRA_ALBUM, playlist.getId());
		// startActivity(intent);
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
		if (playlist.getId() != app.queue.getId()) {
			if (library.playlists.delete(playlist)) {
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
