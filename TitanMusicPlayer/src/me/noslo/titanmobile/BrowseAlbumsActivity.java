package me.noslo.titanmobile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import me.noslo.titanmobile.bll.Album;
import com.example.titanmusicplayer.R;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.ActionBar;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class BrowseAlbumsActivity extends TitanPlayerActivity implements
		OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

	public static final String EXTRA_ARTIST_ID = "me.noslo.titanmobile.extra.ARTIST";

	private long selectedArtistId;

	private ListView list;
	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_albums);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		selectedArtistId = getIntent().getLongExtra(EXTRA_ARTIST_ID, 0);

		list = (ListView) findViewById(R.id.browseAlbumsListView);
		list.setOnItemClickListener(this);

		this.fillList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.browse_library, menu);
		return true;
	}

	private void fillList() {

		String[] from = new String[] { MediaStore.Audio.Media.ALBUM,
				MediaStore.Audio.Media.ARTIST };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

		getLoaderManager().initLoader(0, null, this);
		adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, null, from, to, 0);

		list.setAdapter(adapter);
	}

	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		 Intent intent = new Intent(this, BrowseLibraryActivity.class);
		 intent.putExtra(BrowseLibraryActivity.EXTRA_ALBUM_ID, id);
		 startActivity(intent);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri contentUri;
		String[] projection = { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST };
		if (selectedArtistId > 0) {
			Log.d("BrowseAlbumsActivity", "user artist id");
			contentUri = MediaStore.Audio.Artists.Albums.getContentUri(
					"external", selectedArtistId);
		} else {
			contentUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
		}
		CursorLoader cursorLoader = new CursorLoader(this, contentUri,
				projection, null, null, MediaStore.Audio.Media.ALBUM_KEY);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}
}
