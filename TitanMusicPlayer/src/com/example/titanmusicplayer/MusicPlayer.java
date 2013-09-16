package com.example.titanmusicplayer;

import com.example.titanmusicplayer.bll.Song;
import com.example.titanmusicplayer.bll.SongAdapter;
import com.example.titanmusicplayer.bll.SongList;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class MusicPlayer extends Activity {

	SongList queue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_player);

		this.queue = new SongList();
		this.queue.fetchAll(this);

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
		case R.id.action_browse_library:
			Intent intent = new Intent("android.intent.action.BROWSELIBRARY");
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void updateQueueList() {
		ListView songList = (ListView) findViewById(R.id.currentlyPlayingQueue);
		SongAdapter adapter = new SongAdapter(this, android.R.layout.simple_list_item_2, this.queue.getAll());

		// ArrayAdapter adapter = new ArrayAdapter(this,
		// android.R.layout.simple_list_item_2, android.R.id.text1, list) {
		// @Override
		// public View getView(int position, View convertView, ViewGroup parent)
		// {
		// View view = super.getView(position, convertView, parent);
		// TextView text1 = (TextView) view.findViewById(android.R.id.text1);
		// TextView text2 = (TextView) view.findViewById(android.R.id.text2);
		//
		// Song song = songs.get(position);
		//
		// text1.setText(song.getTitle());
		// text2.setText(song.getArtistAlbum());
		// return view;
		// }
		// };
		songList.setAdapter(adapter);
	}

}

// private void fillSampleData() {
// ListView itemList = (ListView) findViewById(R.id.currentlyPlayingQueue);
//
// List<Map<String, String>> data = new ArrayList<Map<String, String>>();
//
// Map<String, String> song1 = new HashMap<String, String>(2);
// song1.put("title", "Test Song");
// song1.put("album", "Sample Artist - Album");
// data.add(song1);
//
// Map<String, String> song2 = new HashMap<String, String>(2);
// song2.put("title", "Singing Off Key (... And Without Pitch)");
// song2.put("album", "Bill Olson - The Off White Album");
// data.add(song2);
//
// SimpleAdapter adapter = new SimpleAdapter(this, data,
// android.R.layout.simple_list_item_2, new String[] { "title",
// "album" }, new int[] { android.R.id.text1,
// android.R.id.text2 });
//
// itemList.setAdapter(adapter);
// }

