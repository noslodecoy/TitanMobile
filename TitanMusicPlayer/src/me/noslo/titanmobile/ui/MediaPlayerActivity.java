package me.noslo.titanmobile.ui;

import me.noslo.titanmobile.R;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.os.Bundle;
import android.content.Intent;

public class MediaPlayerActivity extends TitanPlayerActivity implements OnItemClickListener {

	//private static final String TAG = "MediaPlayerActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_player);

		FrameLayout mPlaylistFragment = (FrameLayout) findViewById(R.id.currently_playing_queue);
		ListView mPlaylistView = (ListView) mPlaylistFragment.findViewById(android.R.id.list);
		mPlaylistView.setOnItemClickListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
//		library.playlistItems.populate(app.queue);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.music_player, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_browse_library: {
			Intent intent = new Intent("android.intent.action.BROWSELIBRARY");
			startActivity(intent);
			return true;
		}
		case R.id.action_browse_artists: {
			Intent intent = new Intent(this, BrowseArtistsActivity.class);
			startActivity(intent);
			return true;
		}
		case R.id.action_browse_albums: {
			Intent intent = new Intent(this, BrowseAlbumsActivity.class);
			startActivity(intent);
			return true;
		}
		case R.id.action_browse_playlists: {
			Intent intent = new Intent(this, BrowsePlaylistsActivity.class);
			startActivity(intent);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		app.mediaPlayer.setPosition(position);
		PlayerControlsFragment controls = (PlayerControlsFragment) getFragmentManager()
				.findFragmentById(R.id.media_controls);
		controls.play();
	}

}