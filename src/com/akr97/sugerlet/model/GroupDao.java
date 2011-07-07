package com.akr97.sugerlet.model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Groups;

public class GroupDao extends DaoBase<GroupData> {
	static final String TAG = "com.akr97.sugerlet.GroupModel";

	static final String[] PROJECTION = {
		Groups._ID,
		Groups.TITLE,
		Groups.NOTES,
		Groups.SYSTEM_ID,
		Groups.ACCOUNT_NAME,
		Groups.ACCOUNT_TYPE,
		Groups.GROUP_VISIBLE,
		Groups.DELETED
	};

	public GroupDao(Context ctx){
		super(ctx);
	}

	@Override
	public GroupData extract(Cursor c){
		long id = c.getLong(0);
		String title = c.getString(1);
		String notes = c.getString(2);
		String systemId = c.getString(3);
		String accountName = c.getString(4);
		String accountType = c.getString(5);
		int groupVisible = c.getInt(6);
		int deleted = c.getInt(7);

		return new GroupData(id, title, notes, systemId, accountName, accountType,
				groupVisible != 0, deleted != 0);
	}

	public ArrayList<GroupData> getAll(){
		return readRows(getCursor());
	}

	public ArrayList<GroupData> getByAccount(String accountName, String accountType){
		return readRows(getCursor(accountName, accountType));
	}

	public GroupData getById(long id){
		return readRow(getCursor(id));
	}

	public Cursor getCursor(){
		return getContentResolver().query(Groups.CONTENT_URI, PROJECTION, null, null, null);
	}

	public Cursor getCursor(String accountName, String accountType){
		return getContentResolver().query(Groups.CONTENT_URI,
				PROJECTION,
				Groups.ACCOUNT_NAME + "=? AND " +
					Groups.ACCOUNT_TYPE + "=? AND " +
					Groups.DELETED + "=?",
				new String[]{
					accountName,
					accountType,
					String.valueOf(0)},
				Groups._ID);
	}

	public Cursor getCursor(long groupId){
		return getContentResolver().query(Groups.CONTENT_URI,
				PROJECTION, Groups._ID + "=?", 
				new String[]{ String.valueOf(groupId) }, null);
	}
}