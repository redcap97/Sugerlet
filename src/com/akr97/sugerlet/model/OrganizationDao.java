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
}
