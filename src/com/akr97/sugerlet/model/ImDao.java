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
import android.provider.ContactsContract.CommonDataKinds.Im;

public class ImDao extends DaoBase<ImData> {
	static final String TAG = "com.akr97.sugerlet.model.ImModel";

	static final String[] PROJECTION = {
		Im.DATA,
		Im.TYPE,
		Im.LABEL,
		Im.PROTOCOL,
		Im.CUSTOM_PROTOCOL
	};

	public ImDao(Context ctx){
		super(ctx);
	}

	@Override
	public ImData extract(Cursor c) {
		String data = c.getString(0);
		int type = c.getInt(1);
		String label = c.getString(2);
		int protocol = c.getInt(3);
		String customProtocol = c.getString(4);

		return new ImData(data, type, label, protocol, customProtocol);
	}

	public ArrayList<ImData> get(long rawContactId){
		return readRows(getCursor(rawContactId));
	}

	public Cursor getCursor(long rawContactId){
		return getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND " 
					+ Data.MIMETYPE + "=?",
	   			new String[] {
					String.valueOf(rawContactId),
					Im.CONTENT_ITEM_TYPE},
				Data._ID);
	}
	
	public String getTypeLabel(ImData im){
		return Im.getTypeLabel(getResources(), im.type, im.label).toString();
	}

	public String getProtocolLabel(ImData im){
		return Im.getProtocolLabel(getResources(), im.protocol, im.customProtocol).toString();
	}
}
