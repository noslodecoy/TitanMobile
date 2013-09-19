package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.MediaPlayer;
import me.noslo.titanmobile.bll.TitanMobile;
import me.noslo.titanmobile.bll.Song;
import me.noslo.titanmobile.bll.SongListAdapter;
import com.example.titanmusicplayer.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class BrowseLibraryActivity extends Activity {
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

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		// Respond to the action bar's Up/Home button
//		case android.R.id.home:
//			Log.v("PRESSED OPTIONS ITEM", "HOOORRRAYAY: " + selectedAlbumId);
//			if (selectedAlbumId > 0) {
//				Intent intent = new Intent(this, BrowseAlbumsActivity.class);
//				startActivity(intent);
//			} else {
//				NavUtils.navigateUpFromSameTask(this);
//			}
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}

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
	
	protected void addQueueItem( Song song ) {
		TitanMobile.mediaPlayer.getQueue().add( song );
	}
	
	protected Song getListItemSong( int position ) {
	    return (Song)((ListView) findViewById(R.id.browseLibraryListView)).getAdapter().getItem(position);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browse_library, menu);
		return true;
	}

	private void updateQueueList() {
		ListView songList = (ListView) findViewById(R.id.browseLibraryListView);
		SongListAdapter adapter = new SongListAdapter(this, R.layout.song_list_item, TitanMobile.user.library.getSongs(selectedAlbumId));
		songList.setAdapter(adapter);
		registerForContextMenu(songList);
	}

}
