package me.noslo.titanmobile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import me.noslo.titanmobile.BrowseArtistsActivity.ArtistListAdapter;
import me.noslo.titanmobile.bll.Album;
import me.noslo.titanmobile.bll.Artist;

import com.example.titanmusicplayer.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class BrowseAlbumsActivity extends TitanPlayerActivity implements OnItemClickListener {

	public static final String EXTRA_ARTIST = "me.noslo.titanmobile.extra.ARTIST";

	private long mArtistId;
	private ListView mList;
	private ArrayList<Album> mAlbums;
	AlbumListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_albums);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		mArtistId = getIntent().getLongExtra(EXTRA_ARTIST, 0);
		fillList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.browse_library, menu);
		return true;
	}

	private void fillList() {
		mAlbums = user.library.getAlbumArrayList(this, mArtistId);
		mList = (ListView) findViewById(R.id.browseAlbumsListView);
		mAdapter = new AlbumListAdapter(this, android.R.layout.simple_list_item_2,
				android.R.id.text1, mAlbums);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		Album album = mAdapter.getItem(position);
		Intent intent = new Intent(this, BrowseLibraryActivity.class);
		intent.putExtra(BrowseLibraryActivity.EXTRA_ALBUM, album.getId());
		startActivity(intent);
	}

	public class AlbumListAdapter extends ArrayAdapter<Album> {

		private ArrayList<Album> mAlbums;

		public AlbumListAdapter(Context context, int layoutResId, int textViewResourceId,
				ArrayList<Album> albums) {
			super(context, layoutResId, textViewResourceId, albums);
			mAlbums = albums;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			TextView text1 = (TextView) view.findViewById(android.R.id.text1);
			TextView text2 = (TextView) view.findViewById(android.R.id.text2);
			Album album = mAlbums.get(position);
			text1.setText(album.toString());
			text2.setText(album.getArtistName());
			return view;
		}
	}

}
