package me.noslo.titanmobile;

import com.example.titanmusicplayer.R;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.ActionBar;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.LoaderManager;

public class BrowseArtistsActivity extends TitanPlayerActivity implements
		OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

	private ListView list;
	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_artists);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		list = (ListView) findViewById(R.id.browseArtistsListView);
		list.setOnItemClickListener(this);

		this.fillList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.browse_library, menu);
		return true;
	}

	private void fillList() {

		String[] from = new String[] { MediaStore.Audio.Media.ARTIST };
		int[] to = new int[] { android.R.id.text1 };

		getLoaderManager().initLoader(0, null, this);
		adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
				null, from, to, 0);

		list.setAdapter(adapter);
	}

	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		 Intent intent = new Intent(this, BrowseAlbumsActivity.class);
		 intent.putExtra(BrowseAlbumsActivity.EXTRA_ARTIST_ID, id);
		 startActivity(intent);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		Uri contentUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
		String[] projection = { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.ARTIST };
		CursorLoader cursorLoader = new CursorLoader(this, contentUri,
				projection, null, null, MediaStore.Audio.Media.ARTIST_KEY);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		adapter.swapCursor(arg1);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		adapter.swapCursor(null);
	}
}
