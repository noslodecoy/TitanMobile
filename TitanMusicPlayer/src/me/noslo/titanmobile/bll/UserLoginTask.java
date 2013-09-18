package me.noslo.titanmobile.bll;

import me.noslo.titanmobile.MediaPlayerActivity;
import me.noslo.titanmobile.TitanMobileActivity;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;

import com.example.titanmusicplayer.R;

/**
 * Represents an asynchronous login/registration task used to authenticate the
 * user.
 */
public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO: attempt authentication against a network service.

		try {
			// Simulate network access.
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			return false;
		}

		for (String credential : DUMMY_CREDENTIALS) {
			String[] pieces = credential.split(":");
			if (pieces[0].equals(mEmail)) {
				// Account exists, return true if the password matches.
				return pieces[1].equals(mPassword);
			}
		}

		// TODO: register the new account here.
		return true;
	}

	@Override
	protected void onPostExecute(final Boolean success) {
		mAuthTask = null;
		showProgress(false);

		if (success) {
			finish();
			Session.login(mEmail, "");

			SharedPreferences settings = getApplicationContext()
					.getSharedPreferences(TitanMobileActivity.SHARED_PREFS, 0); // 0
			// -
			// for
			// private
			// mode
			Editor editor = settings.edit();
			editor.putString("username", mEmail); // Storing string
			editor.putString("password", mPassword); // Storing string
			editor.commit();

			Intent intent = new Intent(getApplicationContext(),
					MediaPlayerActivity.class);
			startActivity(intent);

		} else {
			mPassword = null;
			mPasswordView
					.setError(getString(R.string.error_incorrect_password));
			mPasswordView.requestFocus();
		}
	}

	@Override
	protected void onCancelled() {
		mAuthTask = null;
		showProgress(false);
	}

}