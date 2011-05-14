package com.akr97.sugerlet.model;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

public class PhoneModel {
	public long id;
	public String number;
	public int type;
	public String label;
	
    static final String TAG = "com.akr97.sugerlet.model.PhoneModel";
    
	static final String[] PROJECTION = new String[]{
		Phone._ID,
		Phone.NUMBER,
		Phone.TYPE,
		Phone.LABEL
	};
	
	static final String RESTRICTION = Data.RAW_CONTACT_ID + "=?" + " AND " 
		+ Data.MIMETYPE + "='" + Phone.CONTENT_ITEM_TYPE + "'";
	
	public PhoneModel(long id, String number, int type, String label){
		this.id = id;
		this.number = number;
		this.type = type;
		this.label = label;
	}
	
	@Override
	public String toString(){
		return String.format("id: %d, number: %s, type: %d, label: %s", 
				this.id, this.number, this.type, this.label);
	}
	
	static public Vector<PhoneModel> get(Context ctx, long contactId){
		Vector<PhoneModel> results = new Vector<PhoneModel>();
		
		Log.d(TAG, "Start to collect phones.");
	   	Cursor c = getCursor(ctx, contactId);
	   	if(c.moveToFirst()){
	   		 do {
	   			 results.add(gainObjectFromCursor(c));
	   		 }while(c.moveToNext());	   		
	   	}
	   	c.close();
	   	
		return results;
	}
	
	static public Cursor getCursor(Context ctx, long contactId){
		return ctx.getContentResolver().query(Data.CONTENT_URI,
				PROJECTION, RESTRICTION,
	   			new String[] {String.valueOf(contactId)}, null);
	}
	
	static public PhoneModel gainObjectFromCursor(Cursor c){
		long id = c.getLong(0);
   		String number = c.getString(1);
   		int type = c.getInt(2);
   		String label = c.getString(3);
   			 
   		return new PhoneModel(id, number, type, label);
	}
}
