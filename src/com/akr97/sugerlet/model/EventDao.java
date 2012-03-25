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
import android.content.res.Resources;
import android.database.Cursor;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.text.TextUtils;

public class EventDao extends DaoBase<EventData> {
	static final String[] PROJECTION = {
		Event.START_DATE,
		Event.TYPE,
		Event.LABEL
	};

	public EventDao(Context context) {
		super(context);
	}

	public ArrayList<EventData> get(long rawContactId){
		return readRows(getCursor(rawContactId));
	}

	public Cursor getCursor(long rawContactId){
		return getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND " 
					+ Data.MIMETYPE + "=?",
				new String[]{
					String.valueOf(rawContactId),
					Event.CONTENT_ITEM_TYPE },
				Event._ID);
	}

	@Override
	public EventData extract(Cursor c) {
		String startDate = c.getString(0);
		int type = c.getInt(1);
		String label = c.getString(2);

		return new EventData(startDate, type, label);
	}

	public String getTypeLabel(EventData event){
		return getTypeLabel(getResources(), event.type, event.label).toString();
	}

	public static final CharSequence getTypeLabel(Resources res, int type,
			CharSequence label) {
		if(type == Event.TYPE_CUSTOM && !TextUtils.isEmpty(label)){
			return label;
		}else{
			final int labelRes = Event.getTypeResource(type);
			return res.getText(labelRes);
		}
	}
}
