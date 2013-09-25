package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.PlaylistItem;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.AlertDialog;
import android.content.Intent;

public class MediaPlayerActivity extends TitanPlayerActivity implements OnItemClickListener,
		OnSeekBarChangeListener {

	private ListView mSongList;
	private SeekBar progressBar;
	private RetrieveQueueTask queueTask;
	PlayListItemAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_player);

		progressBar = (SeekBar) findViewById(R.id.progressBar);
		progressBar.setOnSeekBarChangeListener(this);

		fillList();
		// queueTask = new RetrieveQueueTask();
		// queueTask.execute((Void)null);

	}

	@Override
	protected void onResume() {
		super.onResume();
		
		app.queue.addReplaceAll( library.playlistItems.fetch(app.queue) );
		mAdapter.notifyDataSetChanged();

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
		Log.d("Button pushed", "Play");
		updateProgressBar();
		updateCurrentlyPlaying();
		showNowPlayingDialog();
	}

	public void pause() {
		app.mediaPlayer.pause();
		finishPlaying();
		updateCurrentlyPlaying();
	}

	public void finishPlaying() {
		progressBar.setProgress(0);
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	public void drawPlayBtn() {
		if (app.mediaPlayer.isPlaying()) {
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
			updateProgressBar();
			txt = app.mediaPlayer.getSong().getName();
		}
		TextView txtCurrentlyPlaying = (TextView) findViewById(R.id.txtCurrentlyPlaying);
		txtCurrentlyPlaying.setText(txt);
	}

	public void showNowPlayingDialog() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Play Song");
		alertDialog.setMessage("Playing " + app.mediaPlayer.getSong().getName());
		alertDialog.show();
	}

	public void skipForward(View view) {
		finishPlaying();
		app.mediaPlayer.skipForward();
		updateCurrentlyPlaying();
	}

	public void skipBackward(View view) {
		finishPlaying();
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
		case R.id.action_browse_playlists: {
			Intent intent = new Intent(this, BrowsePlaylistsActivity.class);
			startActivity(intent);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		app.mediaPlayer.setPosition(position);
		play();
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

	protected void removeQueueItem(PlaylistItem song) {
		// user.queue.remove(song);
		
		library.playlistItems.removeFrom( app.queue, song);
		app.queue.remove( song );
		mAdapter.notifyDataSetChanged();
	}

	protected PlaylistItem getListItemSong(int position) {
		return (PlaylistItem) ((ListView) findViewById(R.id.currentlyPlayingQueue)).getAdapter().getItem(
				position);
	}

	private void fillList() {
		mSongList = (ListView) findViewById(R.id.currentlyPlayingQueue);
		mAdapter = new PlayListItemAdapter(this, R.layout.song_list_item, app.queue.getAll());
		mSongList.setAdapter(mAdapter);
		mSongList.setOnItemClickListener(this);
		mSongList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		registerForContextMenu(mSongList);
	}

	private Handler mHandler = new Handler();

	public void updateProgressBar() {
		mHandler.postDelayed(mUpdateTimeTask, 100);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// remove message Handler from updating progress bar
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mHandler.removeCallbacks(mUpdateTimeTask);
		app.mediaPlayer.setProgressPercent(seekBar.getProgress());
		updateProgressBar();
	}

	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			Log.d("UPDATING", "Progress Bar " + app.mediaPlayer.getProgressPercent() + "%");
			int progress = (int) (app.mediaPlayer.getProgressPercent());
			progressBar.setProgress(progress);
			mHandler.postDelayed(this, 100);
		}
	};

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	public class RetrieveQueueTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// app.user.library.getQueue( app.user );
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			queueTask = null;
			// updateQueueList();
		}

		@Override
		protected void onCancelled() {
			queueTask = null;
		}
	}

}