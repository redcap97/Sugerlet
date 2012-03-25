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
import android.provider.ContactsContract.Settings;

public class SettingsDao extends DaoBase<SettingsData> {
	static final String[] PROJECTION = {
		Settings.ACCOUNT_NAME,
		Settings.ACCOUNT_TYPE,
		Settings.UNGROUPED_VISIBLE
	};

	public SettingsDao(Context ctx){
		super(ctx);
	}

	public ArrayList<SettingsData> getAll(){
		return readRows(getCursor());
	}

	public Cursor getCursor(){
		return getContentResolver().query(
				Settings.CONTENT_URI,
				PROJECTION, 
				null, null, null);
	}

	@Override
	public SettingsData extract(Cursor c) {
		String accountName = c.getString(0);
		String accountType = c.getString(1);
		int ungroupedVisible = c.getInt(2);

		return new SettingsData(accountName, accountType, ungroupedVisible);
	}
}
