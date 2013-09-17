package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.Session;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO: Check login information here.  If not logged in, goto login form.
		Session.newSession();
		Intent intent = new Intent(this, MusicPlayer.class);
		startActivity(intent);
	}

}
