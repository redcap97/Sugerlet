package com.akr97.sugerlet.model;


import java.util.Vector;

import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract.Contacts;
import android.util.Log;

public class ContactSummaryModel {
    public int id;
    public String displayName;
    public boolean hasPhoneNumber;
    
    static final String TAG = "com.akr97.sugerlet.model.ContactSummaryModel";
	
	static final String[] PROJECTION = new String[]{
        	Contacts._ID,
        	Contacts.DISPLAY_NAME,
        	Contacts.HAS_PHONE_NUMBER
    };
	
	public ContactSummaryModel(int id, String displayName, boolean hasPhoneNumber){
		this.id = id;
		this.displayName = displayName;
		this.hasPhoneNumber = hasPhoneNumber;
	}
	
	static public Vector<ContactSummaryModel> getAll(Activity act){
		Vector<ContactSummaryModel> results = new Vector<ContactSummaryModel>();
		
		Log.i(TAG, "Start to collect contacts.");
        Cursor c = act.getContentResolver().query(Contacts.CONTENT_URI, PROJECTION, null, null, null);
      	if(c.moveToFirst()){
       		do {
       			int id = c.getInt(0);
       			String displayName = c.getString(1);
        		boolean hasPhoneNumber = (c.getInt(2) == 1);
        		
        		results.add(new ContactSummaryModel(id, displayName, hasPhoneNumber));
        	}while(c.moveToNext());
        }
      	c.close();
        
        return results;
	}
}
