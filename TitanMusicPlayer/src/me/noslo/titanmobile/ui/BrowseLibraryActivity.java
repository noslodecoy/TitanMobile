package me.noslo.titanmobile.ui;

import java.util.ArrayList;

import me.noslo.titanmobile.R;
import me.noslo.titanmobile.TitanApp;
import me.noslo.titanmobile.bll.Album;
import me.noslo.titanmobile.bll.Artist;
import me.noslo.titanmobile.bll.MediaLibraryItem;
import me.noslo.titanmobile.bll.Song;
import me.noslo.titanmobile.dal.AlbumDao;
import me.noslo.titanmobile.dal.PlaylistDao;
import me.noslo.titanmobile.dal.SongDao;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

public class BrowseLibraryActivity extends TitanPlayerActivity implements OnItemClickListener {
	public static final String EXTRA_ALBUM = "me.noslo.titanmobile.extra.ALBUM";

	private Album mAlbum;
	private FetchSongsTask mFetchSongsTask;
	private ListView mList;
	private ArrayList<MediaLibraryItem> mSongs;
	private MediaLibraryAdapter mAdapter;
	
	private static final String TAG = "BrowseLibraryActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_library);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		mSongs = new ArrayList<MediaLibraryItem>();
		loadAlbum();
		fillList();
	}

	private void loadAlbum() {
		long selectedAlbumId = getIntent().getLongExtra(EXTRA_ALBUM, 0);
		if (selectedAlbumId > 0) {
			AlbumDao albumDao = TitanApp.libraryDao.newAlbumDao();
			mAlbum = albumDao.load(selectedAlbumId);
			return;
		}
		mAlbum = null;
	}

	private void fillList() {
		mList = (ListView) findViewById(R.id.browseLibraryListView);
		mAdapter = new MediaLibraryAdapter(this, mSongs);
		mList.setAdapter(mAdapter);
		registerForContextMenu(mList);
		if (mFetchSongsTask == null) {
			mFetchSongsTask = new FetchSongsTask();
			mFetchSongsTask.execute((Void) null);
		}
		mList.setOnItemClickListener(this);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.song_library_item, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.add_queue_item:
			addQueueItem(mAdapter.getItem(info.position));
			return true;
		case R.id.add_to_playlist:
			showAddToPlaylistDialog(mAdapter.getItem(info.position));
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		Log.d( TAG, "Library item clicked (id: "+id+", position: " + position+")" );
		addQueueItem(mAdapter.getItem(position));
	}

	private void showAddToPlaylistDialog(MediaLibraryItem song) {
		Bundle bundle = new Bundle();
		bundle.putLong("song_id", song.getId());

		DialogFragment dialog = new SelectPlaylistDialogFragment();
		dialog.setArguments(bundle);
		dialog.show(getFragmentManager(), "NoticeDialogFragment");
	}

	protected void addQueueItem(MediaLibraryItem song) {
		PlaylistDao playlistDao = TitanApp.libraryDao.newPlaylistDao();
		playlistDao.addMember( app.mediaPlayer.getQueue(), (Song) song);
		
		Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.song_added_to_queue), Toast.LENGTH_SHORT);
		toast.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.browse_library, menu);
		return true;
	}

	public class FetchSongsTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			SongDao songDao = TitanApp.libraryDao.newSongDao();
			mSongs.clear();
			mSongs.addAll(songDao.fetch(mAlbum));
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mFetchSongsTask = null;
			mAdapter.notifyDataSetChanged();
		}

		@Override
		protected void onCancelled() {
			mFetchSongsTask = null;
		}
	}

}
