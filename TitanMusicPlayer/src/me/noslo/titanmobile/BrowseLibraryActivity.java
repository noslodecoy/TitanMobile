package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.Session;
import me.noslo.titanmobile.bll.SongListAdapter;
import com.example.titanmusicplayer.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class BrowseLibraryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_library);
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
		ListView songList = (ListView) findViewById(R.id.browseLibraryListView);
		SongListAdapter adapter = new SongListAdapter(this,
				R.layout.song_list_item, Session.user.library.getSongs());
		songList.setAdapter(adapter);
	}

}
