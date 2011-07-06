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
