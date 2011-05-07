package com.akr97.sugerlet.model;

import java.util.Vector;

import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class PhoneModel {
	public int id;
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
	
	public PhoneModel(int id, String number, int type, String label){
		this.id = id;
		this.number = number;
		this.type = type;
		this.label = label;
	}
	
	static public Vector<PhoneModel> get(Activity act, int contactId){
		Vector<PhoneModel> results = new Vector<PhoneModel>();
	   	Cursor c = act.getContentResolver().query(Data.CONTENT_URI,
	   			PROJECTION, RESTRICTION,
	   			new String[] {String.valueOf(contactId)}, null);
	   	
	   	if(c.moveToFirst()){
	   		 do {
	   			 int id = c.getInt(0);
	   			 String number = c.getString(1);
	   			 int type = c.getInt(2);
	   			 String label = c.getString(3);
	   			 
	   			 results.add(new PhoneModel(id, number, type, label));
	   		 }while(c.moveToNext());	   		
	   	}
	   	 
		return results;
	}
}
