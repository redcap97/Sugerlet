package com.akr97.sugerlet.model;


import java.util.Vector;

import android.content.Context;
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
	
	@Override
	public String toString(){
		return String.format("id: %d, displayName: %s, hasPhoneNumber: %b", 
				this.id, this.displayName, this.hasPhoneNumber);
	}
	
	static public Vector<ContactSummaryModel> getAll(Context ctx){
		Vector<ContactSummaryModel> results = new Vector<ContactSummaryModel>();
		
		Log.i(TAG, "Start to collect contacts.");
        Cursor c = getCursor(ctx);
      	if(c.moveToFirst()){
       		do {
        		results.add(gainObjectFromCursor(c));
        	}while(c.moveToNext());
        }
      	c.close();
        
        return results;
	}
	
	static public Cursor getCursor(Context ctx){
		return ctx.getContentResolver().query(Contacts.CONTENT_URI, PROJECTION, null, null, null);
	}
	
	static public ContactSummaryModel gainObjectFromCursor(Cursor c){
		int id = c.getInt(0);
   		String displayName = c.getString(1);
    	boolean hasPhoneNumber = (c.getInt(2) == 1);
    		
    	return new ContactSummaryModel(id, displayName, hasPhoneNumber);
	}
}
