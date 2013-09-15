package com.example.titanmusicplayer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MusicPlayer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_player);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.music_player, menu);
		return true;
	}

}
