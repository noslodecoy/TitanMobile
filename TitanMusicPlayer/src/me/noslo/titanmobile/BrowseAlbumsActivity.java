package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.Album;
import me.noslo.titanmobile.bll.AlbumListAdapter;
import me.noslo.titanmobile.bll.Artist;
import me.noslo.titanmobile.bll.TitanMobile;
import me.noslo.titanmobile.bll.Song;

import com.example.titanmusicplayer.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class BrowseAlbumsActivity extends Activity implements OnItemClickListener {

	public static final String EXTRA_ARTIST = "me.noslo.titanmobile.extra.ARTIST";

	private int selectedArtistId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_albums);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		selectedArtistId = getIntent().getIntExtra(EXTRA_ARTIST, 0);

		this.updateQueueList();
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		// Respond to the action bar's Up/Home button
//		case android.R.id.home:
//			Log.v("PRESSED OPTIONS ITEM", "HOOORRRAYAY: "+selectedArtistId);
//			if (selectedArtistId > 0) {
//				Intent intent = new Intent(this, BrowseArtistsActivity.class);
//				startActivity(intent);
//			} else {
//				NavUtils.navigateUpFromSameTask(this);
//			}
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browse_library, menu);
		return true;
	}

	private void updateQueueList() {
		ListView songList = (ListView) findViewById(R.id.browseAlbumsListView);
		AlbumListAdapter adapter = new AlbumListAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, TitanMobile.user.library.getAlbums(selectedArtistId));
		songList.setAdapter(adapter);
		songList.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		Album album = getListItemAlbum(position);
		Intent intent = new Intent(this, BrowseLibraryActivity.class);
		intent.putExtra(BrowseLibraryActivity.EXTRA_ALBUM, album.getId());
		startActivity(intent);

	}

	protected Album getListItemAlbum(int position) {
		return (Album) ((ListView) findViewById(R.id.browseAlbumsListView)).getAdapter().getItem(position);
	}
	
	

}
