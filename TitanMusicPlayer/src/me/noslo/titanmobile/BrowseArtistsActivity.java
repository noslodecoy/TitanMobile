package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.Artist;
import me.noslo.titanmobile.bll.ArtistListAdapter;
import me.noslo.titanmobile.bll.TitanMobile;
import com.example.titanmusicplayer.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class BrowseArtistsActivity extends Activity implements OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_artists);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		this.updateQueueList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browse_library, menu);
		return true;
	}

	private void updateQueueList() {
		ArtistListAdapter adapter = new ArtistListAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, TitanMobile.user.library.getArtists());
		ListView songList = (ListView) findViewById(R.id.browseArtistsListView);
		songList.setAdapter(adapter);
		songList.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		Artist artist = getListItemArtist(position);
		Intent intent = new Intent(this, BrowseAlbumsActivity.class);
		intent.putExtra(BrowseAlbumsActivity.EXTRA_ARTIST, artist.getId());
		startActivity(intent);

	}

	protected Artist getListItemArtist(int position) {
		return (Artist) ((ListView) findViewById(R.id.browseArtistsListView)).getAdapter().getItem(position);
	}

}
