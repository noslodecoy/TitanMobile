package me.noslo.titanmobile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import me.noslo.titanmobile.bll.Song;
import com.example.titanmusicplayer.R;
import android.os.Bundle;
import android.app.ActionBar;
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

	private int selectedAlbumId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_library);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		selectedAlbumId = getIntent().getIntExtra(EXTRA_ALBUM, 0);

		this.updateQueueList();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.song_library_item, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.add_queue_item:
			addQueueItem(getListItemSong(info.position));
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	protected void addQueueItem(Song song) {
		app.mediaPlayer.getQueue().add(song);
	}

	protected Song getListItemSong(int position) {
		return (Song) ((ListView) findViewById(R.id.browseLibraryListView))
				.getAdapter().getItem(position);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browse_library, menu);
		return true;
	}

	private void updateQueueList() {
		ArrayList<Song> songs = (selectedAlbumId > 0) ? user.library.getAlbum(
				selectedAlbumId).getSongs() : user.library.getSongs();

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
					return song1.getTitle().compareToIgnoreCase(
							song2.getTitle());
				}
			});
		}

		ListView songList = (ListView) findViewById(R.id.browseLibraryListView);
		SongListAdapter adapter = new SongListAdapter(this,
				R.layout.song_list_item, songs);
		songList.setAdapter(adapter);
		registerForContextMenu(songList);
	}

}
