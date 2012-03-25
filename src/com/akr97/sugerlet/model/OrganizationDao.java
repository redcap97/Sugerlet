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
import android.provider.ContactsContract.CommonDataKinds.Organization;

public class OrganizationDao extends DaoBase<OrganizationData> {
	static final String[] PROJECTION = {
		Organization.COMPANY,
		Organization.TYPE,
		Organization.LABEL,
		Organization.TITLE
	};

	public OrganizationDao(Context context) {
		super(context);
	}

	public ArrayList<OrganizationData> get(long rawContactId){
		return readRows(getCursor(rawContactId));
	}

	public Cursor getCursor(long rawContactId){
		return getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND "
					+ Data.MIMETYPE + "=?",
				new String[]{
				String.valueOf(rawContactId),
				Organization.CONTENT_ITEM_TYPE },
				Organization._ID);
	}

	@Override
	public OrganizationData extract(Cursor c) {
		String company = c.getString(0);
		int type = c.getInt(1);
		String label = c.getString(2);
		String title = c.getString(3);

		return new OrganizationData(company, type, label, title);
	}
	
	public String getTypeLabel(OrganizationData organization){
		return Organization.getTypeLabel(getResources(),
				organization.type, organization.label).toString();
	}
}
