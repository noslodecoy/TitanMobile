package me.noslo.titanmobile;

import java.util.ArrayList;

import me.noslo.titanmobile.bll.Album;
import com.example.titanmusicplayer.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
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

	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// // Respond to the action bar's Up/Home button
	// case android.R.id.home:
	// if (selectedArtistId > 0) {
	// Intent intent = new Intent(this, BrowseArtistsActivity.class);
	// startActivity(intent);
	// } else {
	// NavUtils.navigateUpFromSameTask(this);
	// }
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browse_library, menu);
		return true;
	}

	private void updateQueueList() {
		ListView songList = (ListView) findViewById(R.id.browseAlbumsListView);
		AlbumListAdapter adapter = new AlbumListAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, user.library.getAlbums(selectedArtistId));
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

	public class AlbumListAdapter extends ArrayAdapter<Album> {

		private ArrayList<Album> albums;

		public AlbumListAdapter(Context context, int layoutResId, int textViewResourceId, ArrayList<Album> albums) {
			super(context, layoutResId, textViewResourceId, albums);
			this.albums = albums;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			TextView text1 = (TextView) view.findViewById(android.R.id.text1);
			TextView text2 = (TextView) view.findViewById(android.R.id.text2);
			Album album = albums.get(position);
			text1.setText(album.toString());
			text2.setText(album.getArtist().toString());
			return view;
		}
	}

}
