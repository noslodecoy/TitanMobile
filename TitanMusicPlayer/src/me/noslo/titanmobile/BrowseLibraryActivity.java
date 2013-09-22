package me.noslo.titanmobile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import me.noslo.titanmobile.bll.Song;
import me.noslo.titanmobile.dal.MusicLibraryDAO;

import com.example.titanmusicplayer.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class BrowseLibraryActivity extends TitanPlayerActivity {
	public static final String EXTRA_ALBUM = "me.noslo.titanmobile.extra.ALBUM";

	private int selectedAlbumId;
	private RetrieveDatabaseTask syncTask;
	SyncLocalMediaTask syncMediaTask;
	private ListView songList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_library);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		songList = (ListView) findViewById(R.id.browseLibraryListView);
		syncTask = new RetrieveDatabaseTask();
		syncTask.execute((Void) null);

		selectedAlbumId = getIntent().getIntExtra(EXTRA_ALBUM, 0);
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
			addQueueItem(getListItemSong(info.position));
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	protected void addQueueItem(Song song) {
		Log.d( "BrowseLibraryActivity", "Add Queue Item" );
		app.user.queue.addNew(song);
	}

	protected Song getListItemSong(int position) {
		return (Song) songList.getAdapter().getItem(position);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browse_library, menu);
		return true;
	}

	private void updateQueueList() {
		ArrayList<Song> songs = (selectedAlbumId > 0) ? user.library.getAlbum(selectedAlbumId).getSongs() : user.library.getSongs();

		if (selectedAlbumId > 0) {
			Collections.sort(songs, new Comparator<Song>() {
				public int compare(Song song1, Song song2) {
					if (song1.getTrackNumber() > song2.getTrackNumber()) {
						return 1;
					} else if (song1.getTrackNumber() < song2.getTrackNumber()) {
						return -1;
					}
					return 0;
				}
			});
		} else {
			Collections.sort(songs, new Comparator<Song>() {
				public int compare(Song song1, Song song2) {
					return song1.getTitle().compareToIgnoreCase(song2.getTitle());
				}
			});
		}

		SongListAdapter adapter = new SongListAdapter(this, R.layout.song_list_item, songs);
		songList.setAdapter(adapter);
		registerForContextMenu(songList);
	}

	public class RetrieveDatabaseTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			app.user.library.sync();
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			syncTask = null;
			updateQueueList();
			Log.d( "SYNC", "Start syncing local media" );
			syncMediaTask = new SyncLocalMediaTask();
			syncMediaTask.execute((Void) null);
		}

		@Override
		protected void onCancelled() {
			syncTask = null;
		}
	}

	public class SyncLocalMediaTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			MusicLibraryDAO.scanMedia(user, MusicLibraryDAO.getDb());
			app.user.library.sync();
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			syncMediaTask = null;
			Log.d( "SYNC", "Done syncing local media" );
			
			int index = songList.getFirstVisiblePosition();
			updateQueueList();
			songList.setSelectionFromTop(index, 0);
		}

		@Override
		protected void onCancelled() {
			syncMediaTask = null;
		}
	}

}
