package com.akr97.sugerlet.model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

public class PhoneDao extends DaoBase<PhoneData> {
	static final String TAG = "com.akr97.sugerlet.model.PhoneModel";

	static final String[] PROJECTION = new String[]{
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
				Data._ID);
	}
}
