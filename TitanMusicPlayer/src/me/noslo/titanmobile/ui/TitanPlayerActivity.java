package me.noslo.titanmobile.ui;

import me.noslo.titanmobile.TitanApp;
import me.noslo.titanmobile.bll.User;

import android.app.Activity;
import android.os.Bundle;

public class TitanPlayerActivity extends Activity {

	public TitanApp app;
	public User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.app = (TitanApp) getApplication();
		this.user = app.getUser();
	}
}
