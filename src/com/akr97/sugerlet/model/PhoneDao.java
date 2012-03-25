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
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

public class PhoneDao extends DaoBase<PhoneData> {
	static final String TAG = "com.akr97.sugerlet.model.PhoneModel";

	static final String[] PROJECTION = {
		Phone._ID,
		Phone.NUMBER,
		Phone.TYPE,
		Phone.LABEL
	};

	public PhoneDao(Context ctx){
		super(ctx);
	}

	public PhoneData extract(Cursor c){
		long id = c.getLong(0);
		String number = c.getString(1);
		int type = c.getInt(2);
		String label = c.getString(3);

		return new PhoneData(id, number, type, label);
	}

	public ArrayList<PhoneData> get(long rawContactId){
		Log.d(TAG, "Start to collect phones.");
		return readRows(getCursor(rawContactId));
	}

	public String getTypeLabel(PhoneData phone){
		return Phone.getTypeLabel(getResources(), phone.type, phone.label).toString();
	}

	public Cursor getCursor(long rawContactId){
		return getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND " 
					+ Data.MIMETYPE + "=?",
				new String[] {
					String.valueOf(rawContactId),
					Phone.CONTENT_ITEM_TYPE},
				Phone._ID);
	}
}
