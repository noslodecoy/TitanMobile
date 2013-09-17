package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.Session;
import me.noslo.titanmobile.bll.SongListAdapter;
import com.example.titanmusicplayer.R;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;

public class MusicPlayer extends Activity implements OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_player);

		Session.user.library.sync(this);

		updateQueueList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.music_player, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_browse_library: {
			Intent intent = new Intent("android.intent.action.BROWSELIBRARY");
			startActivity(intent);
			return true;
		}
		case R.id.action_browse_artists: {
			Intent intent = new Intent(this, BrowseArtists.class);
			startActivity(intent);
			return true;
		}
		case R.id.action_browse_albums: {
			Intent intent = new Intent(this, BrowseAlbums.class);
			startActivity(intent);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Play Song");
		alertDialog.setMessage("Playing "
				+ Session.user.library.getSongs().get((int) id).getTitle());
		alertDialog.show();
	}

	private void updateQueueList() {
		ListView songList = (ListView) findViewById(R.id.currentlyPlayingQueue);

		SongListAdapter adapter = new SongListAdapter(this, R.layout.song_list_item, Session.user.library.getSongs());
		songList.setAdapter(adapter);
		songList.setOnItemClickListener(this);
	}

}