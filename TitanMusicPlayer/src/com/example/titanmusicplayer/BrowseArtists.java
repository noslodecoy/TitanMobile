package com.example.titanmusicplayer;

import com.example.titanmusicplayer.bll.ArtistListAdapter;
import com.example.titanmusicplayer.bll.Session;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class BrowseArtists extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_artists);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		this.updateQueueList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browse_library, menu);
		return true;
	}

	private void updateQueueList() {
		ListView songList = (ListView) findViewById(R.id.browseArtistsListView);
		ArtistListAdapter adapter = new ArtistListAdapter(this,
				android.R.layout.simple_list_item_2, android.R.id.text1,
				Session.user.library.getArtists());
		songList.setAdapter(adapter);
	}

}
