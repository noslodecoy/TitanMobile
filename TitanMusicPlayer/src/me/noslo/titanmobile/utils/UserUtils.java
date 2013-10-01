package me.noslo.titanmobile.utils;

import me.noslo.titanmobile.R;
import me.noslo.titanmobile.bll.User;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

public class UserUtils {

	public static User login(Context context, String username, String password) {
		Resources resources = context.getResources();

		TypedArray credentials = resources.obtainTypedArray(R.array.credentials);

		for (int i = 0; i < credentials.length(); i++) {
			String credential = credentials.getString(i);
			String[] pieces = credential.split(":");
			if (pieces[0].equals(username)) {
				if (pieces[1].equals(password)) {
					credentials.recycle();
					User user = new User();
					user.setUserName(username);
					return user;
				}
				credentials.recycle();
				return null;
			}
		}
		credentials.recycle();
		return null;
	}
}
