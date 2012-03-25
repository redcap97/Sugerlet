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
import android.provider.ContactsContract.CommonDataKinds.Email;

public class EmailDao extends DaoBase<EmailData> {
	static final String[] PROJECTION = {
		Email.DATA,
		Email.TYPE,
		Email.LABEL
	};

	public EmailDao(Context ctx){
		super(ctx);
	}

	public EmailData getFirst(long rawContactId){
		return readRow(getCursor(rawContactId));
	}

	public ArrayList<EmailData> get(long rawContactId){
		return readRows(getCursor(rawContactId));
	}

	public Cursor getCursor(long rawContactId){
		return getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND " 
					+ Data.MIMETYPE + "=?",
				new String[]{
					String.valueOf(rawContactId),
					Email.CONTENT_ITEM_TYPE },
				Email._ID);
	}

	@Override
	public EmailData extract(Cursor c) {
		String address = c.getString(0);
		int type = c.getInt(1);
		String label = c.getString(2);

		return new EmailData(address, type, label);
	}
	
	public String getTypeLabel(EmailData email){
		return Email.getTypeLabel(getResources(), email.type, email.label).toString();
	}
}
