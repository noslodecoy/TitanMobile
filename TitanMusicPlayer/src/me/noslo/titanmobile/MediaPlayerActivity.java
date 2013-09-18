package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.MediaPlayer;
import me.noslo.titanmobile.bll.Session;
import me.noslo.titanmobile.bll.SongList;
import me.noslo.titanmobile.bll.SongListAdapter;
import com.example.titanmusicplayer.R;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;

public class MediaPlayerActivity extends Activity implements
		OnItemClickListener {

	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_player);

		Session.user.library.sync(this);

		SongList queue = new SongList();
		queue.addAll(Session.getStoredQueue());
		this.mediaPlayer = new MediaPlayer(queue);

		updateQueueList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.music_player, menu);
		return true;
	}

	public void play() {
		ImageButton btn = (ImageButton) findViewById(R.id.btnPlay);
		btn.setImageResource(R.drawable.pause);
		mediaPlayer.play();
		updateCurrentlyPlaying();
		showNowPlayingDialog();
	}

	public void pause() {
		ImageButton btn = (ImageButton) findViewById(R.id.btnPlay);
		btn.setImageResource(R.drawable.play);
		mediaPlayer.pause();
		updateCurrentlyPlaying();
	}

	public void togglePlay(View view) {
		if (mediaPlayer.isPlaying()) {
			pause();
		} else {
			play();
		}
	}

	public void updateCurrentlyPlaying() {
		String txt = "";
		if (mediaPlayer.isPlaying()) {
			txt = mediaPlayer.getSong().getTitle();
		}
		TextView txtCurrentlyPlaying = (TextView) findViewById(R.id.txtCurrentlyPlaying);
		txtCurrentlyPlaying.setText(txt);
	}

	public void showNowPlayingDialog() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Play Song");
		alertDialog.setMessage("Playing "
				+ this.mediaPlayer.getSong().getTitle());
		alertDialog.show();
	}

	public void skipForward(View view) {
		mediaPlayer.skipForward();
	}

	public void skipBackward(View view) {
		mediaPlayer.skipBackward();
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
			Intent intent = new Intent(this, BrowseArtistsActivity.class);
			startActivity(intent);
			return true;
		}
		case R.id.action_browse_albums: {
			Intent intent = new Intent(this, BrowseAlbumsActivity.class);
			startActivity(intent);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		mediaPlayer.setPosition(position);
		play();
		updateList();
	}

	private void updateList() {
		// ListView songList = (ListView)
		// findViewById(R.id.currentlyPlayingQueue);
		// for (int i = 0; i < songList.getAdapter().getCount(); i++) {
		// Log.v("test", i + " == " + mediaPlayer.getPosition());
		// View v = songList.getAdapter().getView(i, null, songList);
		// ImageView img = (ImageView) v.findViewById(R.id.isPlaying);
		// if (i == mediaPlayer.getPosition()) {
		// img.setImageResource(R.drawable.play);
		// } else {
		// img.setImageResource(R.drawable.blank);
		// }
		// }
		// ImageView img = (ImageView) v.findViewById( R.id.isPlaying);
		// img.setImageResource(R.drawable.play);
	}

	private void updateQueueList() {
		SongListAdapter adapter = new SongListAdapter(this,
				R.layout.song_list_item, this.mediaPlayer.getQueue().getAll());
		ListView songList = (ListView) findViewById(R.id.currentlyPlayingQueue);
		songList.setAdapter(adapter);
		songList.setOnItemClickListener(this);
	}

}