package me.noslo.titanmobile.bll;


public class User {

	private String mUsername;

	public User() {
	}

	public String getUsername() {
		return mUsername;
	}

	public void setUserName(String username) {
		mUsername = username;
	}

	public boolean isLoggedIn() {
		return (mUsername != null);
	}

}
