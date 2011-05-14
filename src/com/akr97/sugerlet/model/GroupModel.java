package com.akr97.sugerlet.model;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Groups;
import android.util.Log;

public class GroupModel {
	public long id;
	public String title;
	public String notes;
	public String systemId;
	public String accountName;
	public String accountType;
	
	static final String TAG = "com.akr97.sugerlet.GroupModel";
	
	static final String[] PROJECTION = new String[]{
    	Groups._ID,
    	Groups.TITLE,
    	Groups.NOTES,
    	Groups.SYSTEM_ID,
    	Groups.ACCOUNT_NAME,
    	Groups.ACCOUNT_TYPE
    };
	
	public GroupModel(long id, String title, String notes, String systemId, 
			String accountName, String accountType){
		this.id = id;
		this.title = title;
		this.notes = notes;
		this.systemId = systemId;
		this.accountName = accountName;
		this.accountType = accountType;
	}
	
	@Override
	public String toString(){
		return String.format("id: %d, title: %s, notes: %s, systemId: %s"
					+ " accountName: %s, accountType: %s", 
				this.id, this.title, this.notes, this.systemId,
				this.accountName, this.accountType);
	}
	
	static public Vector<GroupModel> getAll(Context ctx){
		Vector<GroupModel> results = new Vector<GroupModel>();
		
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
		return ctx.getContentResolver().query(Groups.CONTENT_URI, PROJECTION, null, null, null);
	}
	
	static public GroupModel gainObjectFromCursor(Cursor c){
       	long id = c.getLong(0);
    	String title = c.getString(1);
    	String notes = c.getString(2);
    	String systemId = c.getString(3);
    	String accountName = c.getString(4);
    	String accountType = c.getString(5);
    	
    	return new GroupModel(id, title, notes, systemId, accountName, accountType);
	}
}