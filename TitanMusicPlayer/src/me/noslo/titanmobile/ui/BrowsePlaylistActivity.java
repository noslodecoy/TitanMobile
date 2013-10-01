package me.noslo.titanmobile.ui;

import me.noslo.titanmobile.R;
import android.app.ActionBar;
import android.os.Bundle;

public class BrowsePlaylistActivity extends TitanPlayerActivity {

	public static final String EXTRA_PLAYLIST = "me.noslo.titanmobile.extra.EXTRA_PLAYLIST";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_playlist);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
}
