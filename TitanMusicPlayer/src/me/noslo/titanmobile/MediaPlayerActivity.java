package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.Song;
import me.noslo.titanmobile.dal.MusicLibraryDAO;
import com.example.titanmusicplayer.R;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;

public class MediaPlayerActivity extends TitanPlayerActivity implements OnItemClickListener {

	private ListView songList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_music_player);

		updateQueueList();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		((BaseAdapter) songList.getAdapter()).notifyDataSetChanged();
		drawPlayBtn();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.music_player, menu);
		return true;
	}

	public void play() {
		app.mediaPlayer.play();
		updateCurrentlyPlaying();
		showNowPlayingDialog();
	}

	public void pause() {
		app.mediaPlayer.pause();
		updateCurrentlyPlaying();
	}
	
	public void drawPlayBtn() {
		if ( app.mediaPlayer.isPlaying() ) {
			ImageButton btn = (ImageButton) findViewById(R.id.btnPlay);
			btn.setImageResource(R.drawable.pause);
		} else {
			ImageButton btn = (ImageButton) findViewById(R.id.btnPlay);
			btn.setImageResource(R.drawable.play);
		}
	}
	
	public void togglePlay(View view) {
		if (app.mediaPlayer.isPlaying()) {
			pause();
		} else {
			play();
		}
	}

	public void updateCurrentlyPlaying() {
		drawPlayBtn();
		String txt = "";
		if (app.mediaPlayer.isPlaying()) {
			txt = app.mediaPlayer.getSong().getTitle();
		}
		TextView txtCurrentlyPlaying = (TextView) findViewById(R.id.txtCurrentlyPlaying);
		txtCurrentlyPlaying.setText(txt);
	}

	public void showNowPlayingDialog() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Play Song");
		alertDialog.setMessage("Playing " + app.mediaPlayer.getSong().getTitle());
		alertDialog.show();
	}

	public void skipForward(View view) {
		app.mediaPlayer.skipForward();
		updateCurrentlyPlaying();
	}

	public void skipBackward(View view) {
		app.mediaPlayer.skipBackward();
		updateCurrentlyPlaying();
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

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		app.mediaPlayer.setPosition(position);
		view.setSelected(true);
		play();
		view.setSelected( true );
		((BaseAdapter) songList.getAdapter()).notifyDataSetChanged();

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.song_queue_item, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.remove_queue_item:
			removeQueueItem(getListItemSong(info.position));
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	protected void removeQueueItem(Song song) {
		user.queue.remove(song);
		((BaseAdapter) songList.getAdapter()).notifyDataSetChanged(); 
	}

	protected Song getListItemSong(int position) {
		return (Song) ((ListView) findViewById(R.id.currentlyPlayingQueue)).getAdapter().getItem(position);
	}

	private void updateQueueList() {
		SongListAdapter adapter = new SongListAdapter(this, R.layout.song_list_item, user.queue.getAll());
		this.songList = (ListView) findViewById(R.id.currentlyPlayingQueue);
		songList.setAdapter(adapter);
		songList.setOnItemClickListener(this);
		songList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		registerForContextMenu(songList);
	}

}