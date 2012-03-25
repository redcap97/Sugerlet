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

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.RawContacts;

public class RawContactsDao extends DaoBase<RawContactsData> {
	static final String[] PROJECTION = {
		RawContacts._ID,
		RawContacts.CONTACT_ID,
		RawContacts.STARRED,
		RawContacts.ACCOUNT_NAME,
		RawContacts.ACCOUNT_TYPE
	};

	public RawContactsDao(Context context) {
		super(context);
	}

	public RawContactsData get(long id){
		return readRow(getCursor(id));
	}

	public Cursor getCursor(long id){
		return getContentResolver().query(RawContacts.CONTENT_URI,
				PROJECTION,
				RawContacts._ID + "=?",
				new String[]{ String.valueOf(id) },
				null);
	}

	@Override
	public RawContactsData extract(Cursor c) {
		long id = c.getLong(0);
		long contactId = c.getLong(1);
		int starred = c.getInt(2);
		String accountName = c.getString(3);
		String accountType = c.getString(4);

		return new RawContactsData(id, contactId,
				starred != 0, accountName, accountType);
	}
}
