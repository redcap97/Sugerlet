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
import android.provider.ContactsContract.CommonDataKinds.Website;

public class WebsiteDao extends DaoBase<WebsiteData> {
	static final String[] PROJECTION = {
		Website.URL,
		Website.TYPE,
		Website.LABEL
	};

	public WebsiteDao(Context ctx){
		super(ctx);
	}

	public ArrayList<WebsiteData> get(long rawContactId){
		return readRows(getCursor(rawContactId));
	}

	public Cursor getCursor(long rawContactId){
		return getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND " 
					+ Data.MIMETYPE + "=?",
				new String[]{
					String.valueOf(rawContactId),
					Website.CONTENT_ITEM_TYPE },
				Website._ID);
	}

	@Override
	public WebsiteData extract(Cursor c) {
		String url = c.getString(0);
		int type = c.getInt(1);
		String label = c.getString(2);

		return new WebsiteData(url, type, label);
	}
}
