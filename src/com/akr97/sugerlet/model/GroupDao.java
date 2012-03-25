/*
Copyright (C) 2012 Akira Midorikawa

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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