package me.noslo.titanmobile;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class MainActivity extends Activity {

	public static final String SHARED_PREFS = "me.noslo.titanmobile.PREFS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Check login information here. If not logged in, request credentials.

		SharedPreferences settings = getSharedPreferences(
				MainActivity.SHARED_PREFS, 0);
		String username = settings.getString("username", null);
		String password = settings.getString("password", null);

		Intent intent = new Intent(this, LoginActivity.class);

		intent.putExtra(LoginActivity.EXTRA_EMAIL, username);
		intent.putExtra(LoginActivity.EXTRA_PASSWORD, password);

		startActivity(intent);
	}

}
