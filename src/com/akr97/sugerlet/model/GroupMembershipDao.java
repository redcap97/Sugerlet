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
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;

public class GroupMembershipDao extends DaoBase<GroupData> {
	private GroupDao groupDao;

	static final String[] PROJECTION = {
		GroupMembership.GROUP_ROW_ID
	};

	public GroupMembershipDao(Context ctx){
		super(ctx);
		this.groupDao = new GroupDao(ctx);
	}

	public ArrayList<GroupData> get(long rawContactId){
		return readRows(getCursor(rawContactId));
	}

	public Cursor getCursor(long rawContactId){
		return getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND " 
					+ Data.MIMETYPE + "=?",
				new String[]{
					String.valueOf(rawContactId),
					GroupMembership.CONTENT_ITEM_TYPE },
				GroupMembership._ID);
	}

	@Override
	public GroupData extract(Cursor c) {
		long groupRowId = c.getLong(0);
		return groupDao.getById(groupRowId);
	}
}
