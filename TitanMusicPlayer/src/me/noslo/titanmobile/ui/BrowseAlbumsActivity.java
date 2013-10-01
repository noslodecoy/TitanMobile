package me.noslo.titanmobile.ui;

import java.util.ArrayList;

import me.noslo.titanmobile.R;
import me.noslo.titanmobile.TitanApp;
import me.noslo.titanmobile.bll.Album;
import me.noslo.titanmobile.bll.Artist;
import me.noslo.titanmobile.bll.MediaLibraryItem;
import me.noslo.titanmobile.dal.AlbumDao;
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

public class BrowseAlbumsActivity extends TitanPlayerActivity implements OnItemClickListener {

	public static final String EXTRA_ARTIST = "me.noslo.titanmobile.extra.ARTIST";

	private ListView mList;
	private ArrayList<MediaLibraryItem> mAlbums;
	private Artist mArtist;
	MediaLibraryAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_albums);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		getArtist();
		

		
		fillList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.browse_library, menu);
		return true;
	}
	
	private void getArtist() {
		long artistId = getIntent().getLongExtra(EXTRA_ARTIST, 0);
		if ( artistId > 0 ) {
			ArtistDao artistDao = TitanApp.libraryDao.newArtistDao();
			mArtist = artistDao.load( artistId );
		} else {
			mArtist = null;
		}
	}

	private void fillList() {
		Log.d("ARTIST", "Selected artist: "+mArtist);
		AlbumDao artistDao = TitanApp.libraryDao.newAlbumDao();
		
		mAlbums = artistDao.fetch(mArtist);

		mList = (ListView) findViewById(R.id.browseAlbumsListView);
		mAdapter = new MediaLibraryAdapter( this, mAlbums );
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		Album album = (Album) mAdapter.getItem(position);
		Intent intent = new Intent(this, BrowseLibraryActivity.class);
		intent.putExtra(BrowseLibraryActivity.EXTRA_ALBUM, album.getId());
		startActivity(intent);
	}

}
