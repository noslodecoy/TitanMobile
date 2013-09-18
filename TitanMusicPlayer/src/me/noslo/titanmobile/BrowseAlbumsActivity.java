package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.AlbumListAdapter;
import me.noslo.titanmobile.bll.Session;
import com.example.titanmusicplayer.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class BrowseAlbumsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_albums);
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
		ListView songList = (ListView) findViewById(R.id.browseAlbumsListView);
		AlbumListAdapter adapter = new AlbumListAdapter(this,
				android.R.layout.simple_list_item_2, android.R.id.text1,
				Session.user.library.getAlbums());
		songList.setAdapter(adapter);
	}

}
