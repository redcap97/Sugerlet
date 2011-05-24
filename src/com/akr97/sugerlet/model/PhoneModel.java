package com.akr97.sugerlet.model;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

public class PhoneModel extends ModelBase<PhoneData> {
	private Context ctx;
	
    static final String TAG = "com.akr97.sugerlet.model.PhoneModel";
    
	static final String[] PROJECTION = new String[]{
		Phone._ID,
		Phone.NUMBER,
		Phone.TYPE,
		Phone.LABEL
	};
	
	public PhoneModel(Context ctx){
		this.ctx = ctx;
	}
	
	public PhoneData extract(Cursor c){
		long id = c.getLong(0);
   		String number = c.getString(1);
   		int type = c.getInt(2);
   		String label = c.getString(3);

   		return new PhoneData(id, number, type, label);
	}
	
	public Vector<PhoneData> get(long rawContactId){
		Log.d(TAG, "Start to collect phones.");
		return readRows(getCursor(rawContactId));
	}
	
	public Cursor getCursor(long rawContactId){
		return ctx.getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND " 
					+ Data.MIMETYPE + "=?",
	   			new String[] {
					String.valueOf(rawContactId),
					Phone.CONTENT_ITEM_TYPE},
				Data._ID);
	}
}
