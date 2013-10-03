package me.noslo.titanmobile.dal;

public interface AccountDao {

	public boolean authenticate(String username, String password);

	//public boolean exists(String username);
	
	public boolean setUsername(String username, String password, String newUsername);

	public boolean setPassword(String username, String password, String newPassword);

	public boolean create(String username, String password);
	
	public void close();

}
