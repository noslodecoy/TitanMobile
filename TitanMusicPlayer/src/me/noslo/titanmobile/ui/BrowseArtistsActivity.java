package me.noslo.titanmobile.ui;

import java.util.ArrayList;

import me.noslo.titanmobile.R;
import me.noslo.titanmobile.TitanApp;
import me.noslo.titanmobile.bll.Artist;
import me.noslo.titanmobile.bll.MediaLibraryItem;
import me.noslo.titanmobile.dal.ArtistDao;

import android.os.Bundle;
import android.app.ActionBar;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class BrowseArtistsActivity extends TitanPlayerActivity implements OnItemClickListener {

	private ListView mList;
	private ArrayList<MediaLibraryItem> mArtists;
	MediaLibraryAdapter mAdapter;

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
		
		ArtistDao artistDao = TitanApp.libraryDao.newArtistDao();
		
		mArtists = artistDao.fetchAll();
		mAdapter = new MediaLibraryAdapter(this, mArtists);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		Artist artist = (Artist)mAdapter.getItem(position);
		Log.d("browseArtists", "artist (" + artist.getId() + "): " + artist.getName());
		Intent intent = new Intent(this, BrowseAlbumsActivity.class);
		intent.putExtra(BrowseAlbumsActivity.EXTRA_ARTIST, artist.getId());
		startActivity(intent);
	}

}
