package me.noslo.titanmobile;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.Artist;

import android.os.Bundle;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class BrowseArtistsActivity extends TitanPlayerActivity implements OnItemClickListener {

	private ListView mList;
	private ArrayList<Artist> mArtists;
	private Context mContext;
	ArtistListAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_artists);
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
		mList = (ListView) findViewById(R.id.browseArtistsListView);
		mArtists = library.artists.fetchAll();
		mAdapter = new ArtistListAdapter(this,
				android.R.layout.simple_list_item_2, android.R.id.text1, mArtists);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		Artist artist = mAdapter.getItem(position);
		Log.d( "browseArtists", "artist ("+artist.getId()+"): " +artist.getName() );
		Intent intent = new Intent(this, BrowseAlbumsActivity.class);
		intent.putExtra(BrowseAlbumsActivity.EXTRA_ARTIST, artist.getId());
		startActivity(intent);
	}

	public class ArtistListAdapter extends ArrayAdapter<Artist> {

		public ArtistListAdapter(Context context, int layoutResId, int textViewResourceId,
				ArrayList<Artist> artists) {
			super(context, layoutResId, textViewResourceId, artists);
		}

	}
}
