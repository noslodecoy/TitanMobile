package me.noslo.titanmobile.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AccountDaoLocal implements AccountDao {

	private SQLiteDatabase database;
	private AccountDataHelper dbHelper;
	private String[] allColumns = { AccountDataHelper.COLUMN_ID, AccountDataHelper.COLUMN_USERNAME,
			AccountDataHelper.COLUMN_PASSWORD };
	private final String USERNAME_PASSWORD_SELECTION = AccountDataHelper.COLUMN_USERNAME
			+ "=? AND " + AccountDataHelper.COLUMN_PASSWORD + "=?";

	public AccountDaoLocal(Context context) {
		dbHelper = new AccountDataHelper(context);
		database = dbHelper.getWritableDatabase();
	}

	@Override
	public void close() {
		dbHelper.close();
	}

	@Override
	public boolean authenticate(String username, String password) {
		String[] columns = new String[] { AccountDataHelper.COLUMN_USERNAME };
		String selection = this.USERNAME_PASSWORD_SELECTION;
		String[] selectionArgs = new String[] { username, password };
		String groupBy = null;
		String having = null;
		String orderBy = null;
		String limit = null;
		Cursor cursor = database.query(AccountDataHelper.TABLE_NAME, columns, selection,
				selectionArgs, groupBy, having, orderBy, limit);
		boolean validated = (cursor.getCount() > 0);
		cursor.close();
		return validated;
	}

	@Override
	public boolean setUsername(String username, String password, String newUsername) {
		ContentValues values = new ContentValues();
		values.put(AccountDataHelper.COLUMN_USERNAME, username);
		String whereClause = this.USERNAME_PASSWORD_SELECTION;
		String[] whereArgs = new String[] { username, password };
		int affectedRows = database.update(AccountDataHelper.TABLE_NAME, values, whereClause, whereArgs);
		return (affectedRows > 0 );
	}

	@Override
	public boolean setPassword(String username, String password, String newPassword) {
		ContentValues values = new ContentValues();
		values.put(AccountDataHelper.COLUMN_PASSWORD, password);
		String whereClause = this.USERNAME_PASSWORD_SELECTION;
		String[] whereArgs = new String[] { username, password };
		int affectedRows = database.update(AccountDataHelper.TABLE_NAME, values, whereClause, whereArgs);
		return (affectedRows > 0 );
	}

	@Override
	public boolean create(String username, String password) {
		if (!this.exists(username)) {
			ContentValues values = new ContentValues();
			values.put(AccountDataHelper.COLUMN_USERNAME, username);
			values.put(AccountDataHelper.COLUMN_PASSWORD, password);
			long insertId = database.insert(AccountDataHelper.TABLE_NAME, null, values);
			if (insertId > 0) {
				return true;
			}
		}
		return false;
	}

	private boolean exists(String username) {
		String[] columns = new String[] { AccountDataHelper.COLUMN_USERNAME };
		String selection = AccountDataHelper.COLUMN_USERNAME + "=?";
		String[] selectionArgs = new String[] { username };
		String groupBy = null;
		String having = null;
		String orderBy = null;
		String limit = null;
		Cursor cursor = database.query(AccountDataHelper.TABLE_NAME, columns, selection,
				selectionArgs, groupBy, having, orderBy, limit);
		boolean exists = (cursor.getCount() > 0);
		cursor.close();
		return exists;
	}

}
