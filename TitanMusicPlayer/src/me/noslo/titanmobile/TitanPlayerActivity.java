package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.User;
import me.noslo.titanmobile.dal.MediaLibraryDAO;
import android.app.Activity;
import android.os.Bundle;

public class TitanPlayerActivity extends Activity {

	public TitanPlayerApplication app;
	public User user;
	public MediaLibraryDAO library;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.app = (TitanPlayerApplication) getApplication();
		this.user = app.user;
		library = app.library;
	}
}
