package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.MediaPlayer;
import me.noslo.titanmobile.dal.MusicLibraryDAO;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class TitanMobileActivity extends Activity {

	public static final String SHARED_PREFS = "me.noslo.titanmobile.PREFS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MusicLibraryDAO.context = this;
		
		// Check login information here. If not logged in, request credentials.

		SharedPreferences settings = getSharedPreferences(
				TitanMobileActivity.SHARED_PREFS, 0);
		String username = settings.getString("username", null);
		String password = settings.getString("password", null);

		Intent intent = new Intent(this, LoginActivity.class);

		intent.putExtra(LoginActivity.EXTRA_EMAIL, username);
		intent.putExtra(LoginActivity.EXTRA_PASSWORD, password);

		startActivity(intent);
	}

}
