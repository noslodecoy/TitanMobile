package com.example.titanmusicplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class MusicPlayer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_player);
		fillSampleData();
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

	private void fillSampleData() {
		ListView itemList = (ListView) findViewById(R.id.currentlyPlayingQueue);

		List<Map<String, String>> data = new ArrayList<Map<String, String>>();

		Map<String, String> song1 = new HashMap<String, String>(2);
		song1.put("title", "Test Song");
		song1.put("album", "Sample Artist - Album");
		data.add(song1);
		
		Map<String, String> song2 = new HashMap<String, String>(2);
		song2.put("title", "Singing Off Key (... And Without Pitch)");
		song2.put("album", "Bill Olson - The Off White Album");
		data.add(song2);

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_2, new String[] { "title",
						"album" }, new int[] { android.R.id.text1,
						android.R.id.text2 });

		itemList.setAdapter(adapter);
	}

}
