package me.noslo.titanmobile.bll;


public class User {

	private String mUsername;
	private boolean newUser;

	public User() {
		newUser = false;
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
	
	public void setNewUser() {
		newUser = true;
	}
	
	public boolean isNewUser() {
		return newUser;
	}

}
