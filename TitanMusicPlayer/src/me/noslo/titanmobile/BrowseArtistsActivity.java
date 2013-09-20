package me.noslo.titanmobile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import me.noslo.titanmobile.bll.Artist;
import com.example.titanmusicplayer.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class BrowseArtistsActivity extends TitanPlayerActivity implements
		OnItemClickListener {

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
		ArrayList<Artist> artists = user.library.getArtists();
		Collections.sort(artists, new Comparator<Artist>() {
			public int compare(Artist artist1, Artist artist2) {
				return artist1.toString().compareToIgnoreCase(
						artist2.toString());
			}
		});

		ArtistListAdapter adapter = new ArtistListAdapter(this,
				android.R.layout.simple_list_item_2, android.R.id.text1,
				artists);
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
		return (Artist) ((ListView) findViewById(R.id.browseArtistsListView))
				.getAdapter().getItem(position);
	}

	public class ArtistListAdapter extends ArrayAdapter<Artist> {

		ArrayList<Artist> artists;

		public ArtistListAdapter(Context context, int layoutResId,
				int textViewResourceId, ArrayList<Artist> artists) {
			super(context, layoutResId, textViewResourceId, artists);
		}

	}
}
