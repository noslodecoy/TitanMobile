package me.noslo.titanmobile;

import java.util.ArrayList;
import me.noslo.titanmobile.bll.Song;
import com.example.titanmusicplayer.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class BrowseLibraryActivity extends TitanPlayerActivity {
	public static final String EXTRA_ALBUM = "me.noslo.titanmobile.extra.ALBUM";

	private long mSelectedAlbumId;
	private FetchSongsTask mFetchSongsTask;
	private ListView mList;
	private ArrayList<Song> mSongs;
	private Context mContext;
	SongListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_library);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		mSelectedAlbumId = getIntent().getLongExtra(EXTRA_ALBUM, 0);
		mSongs = new ArrayList<Song>();
		mContext = this;
		fillList();
	}
	
	private void fillList() {
		mList = (ListView) findViewById(R.id.browseLibraryListView);
		mAdapter = new SongListAdapter(this, R.layout.song_list_item, mSongs);
		mList.setAdapter(mAdapter);
		registerForContextMenu(mList);
		if ( mFetchSongsTask == null ) {
			mFetchSongsTask = new FetchSongsTask();
			mFetchSongsTask.execute((Void) null);
		}
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
		default:
			return super.onContextItemSelected(item);
		}
	}

	protected void addQueueItem(Song song) {
		app.user.queue.addNew(song);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.browse_library, menu);
		return true;
	}

	public class FetchSongsTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			mSongs.clear();
			mSongs.addAll(app.user.library.getSongArrayList(mContext, mSelectedAlbumId));
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
