package me.noslo.titanmobile.utils;

import me.noslo.titanmobile.R;
import me.noslo.titanmobile.TitanApp;
import me.noslo.titanmobile.bll.User;
import me.noslo.titanmobile.dal.AccountDao;
import me.noslo.titanmobile.dal.AccountDaoLocal;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.util.Log;

public class UserUtils {
	
	private static final String TAG = UserUtils.class.getName();
	

	public static User authenticate(Context context, String username, String password) {
		Log.d( TAG, "authenticating..." );
		Resources resources = context.getResources();

		AccountDao accountDao = new AccountDaoLocal(context);
		boolean authenticated = accountDao.authenticate( username, password );
		accountDao.close();
		
		if ( authenticated) {
			return UserUtils.getUser( context, username, password );
		}
		
		return create( context, username, password );
	}
	
	public static User create( Context context, String username, String password ) {
		Log.d( TAG, "creating..." );
		AccountDao accountDao = new AccountDaoLocal(context);
		if ( accountDao.create( username, password ) ) {
			return getUser( context, username, password );
		}
		return null;
	}
	
	
	private static User getUser( Context context, String username, String password ) {
		Log.d( TAG, "setting user..." );
		User user = new User();
		user.setUserName(username);
		UserUtils.setStoredUsername(context, username);
		UserUtils.setStoredPassword(context, password);
		return user;
	}

	public static String getStoredUsername(Context context) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		return settings.getString("username", null);
	}

	public static String getStoredPassword(Context context) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		return settings.getString("password", null);
	}

	public static void setStoredUsername(Context context, String email) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = settings.edit();
		editor.putString("username", email); // Storing string
		editor.commit();

	}

	public static void setStoredPassword(Context context, String password) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = settings.edit();
		editor.putString("password", password); // Storing string
		editor.commit();
	}

	public static boolean logout(Context context) {
		setStoredUsername( context, null );		
		setStoredPassword( context, null );
		return true;
	}

}
