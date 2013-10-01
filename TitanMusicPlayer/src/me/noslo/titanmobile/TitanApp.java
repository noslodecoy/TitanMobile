package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.MediaQueuePlayer;
import me.noslo.titanmobile.bll.User;
import me.noslo.titanmobile.dal.MediaLibraryDaoFactory;
import me.noslo.titanmobile.utils.LibraryUtils;

import android.app.Application;

public class TitanApp extends Application {

	private User mUser;
	public MediaQueuePlayer mediaPlayer;
	public static MediaLibraryDaoFactory libraryDao;
	
	//private static final String TAG = "TitanPlayerApplication";

	public static final String SHARED_PREFS = "me.noslo.titanmobile.PREFS";

	@Override
	public void onCreate() {
		libraryDao = new MediaLibraryDaoFactory( this );
		mediaPlayer = new MediaQueuePlayer(this, LibraryUtils.getQueue(this));
		mUser = new User();
	}
	
	public void setUser( User user ) {
		mUser = user;
	}
	
	public User getUser() {
		return mUser;
	}
	
}
