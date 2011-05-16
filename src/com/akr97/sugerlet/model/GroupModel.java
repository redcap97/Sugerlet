package com.akr97.sugerlet.model;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Groups;
import android.util.Log;
import com.akr97.sugerlet.*;

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
	
	static public Vector<GroupModel> get(Context ctx){
		Vector<GroupModel> groups;
		AccountStateManager accountChanger = AccountStateManagerFactory.create(ctx);
		if(accountChanger.hasFilter()){
			groups = new Vector<GroupModel>();
			for(AccountStateManager.State s : accountChanger){
				if(s.isEnabled()){
					groups.addAll(getByAccount(ctx, s.getName(), s.getType()));
				}
			}
		}else{
			groups = getAll(ctx);
		}
		
		return groups;
	}
	
	static public Vector<GroupModel> getAll(Context ctx){
        Cursor c = getCursor(ctx);
        return readRowsFromCursor(c);
	}

	static public Vector<GroupModel> getByAccount(Context ctx, String accountName, String accountType){
		Cursor c = getCursor(ctx, accountName, accountType);
		return readRowsFromCursor(c);
	}
	
	static public GroupModel getById(Context ctx, long id){
		Cursor c = getCursor(ctx, id);
		if(c.moveToFirst()){
			return extractObjectFromCursor(c);
		}
		return null;
	}

	static public Cursor getCursor(Context ctx){
		return ctx.getContentResolver().query(Groups.CONTENT_URI, PROJECTION, null, null, null);
	}
	
	static public Cursor getCursor(Context ctx, String accountName, String accountType){
		return ctx.getContentResolver().query(Groups.CONTENT_URI,
				PROJECTION, Groups.ACCOUNT_NAME + "=? AND " + Groups.ACCOUNT_TYPE + "=?", 
				new String[]{ accountName, accountType }, Groups._ID);
	}
	
	static public Cursor getCursor(Context ctx, long groupId){
		return ctx.getContentResolver().query(Groups.CONTENT_URI,
				PROJECTION, Groups._ID + "=?", 
				new String[]{ String.valueOf(groupId) }, null);
	}

	static public Vector<GroupModel> readRowsFromCursor(Cursor c){
		Vector<GroupModel> results = new Vector<GroupModel>();
		
		if(c.moveToFirst()){
			do {
				results.add(extractObjectFromCursor(c));
			}while(c.moveToNext());
		}
		return results;
	}
	
	static public GroupModel extractObjectFromCursor(Cursor c){
       	long id = c.getLong(0);
    	String title = c.getString(1);
    	String notes = c.getString(2);
    	String systemId = c.getString(3);
    	String accountName = c.getString(4);
    	String accountType = c.getString(5);
    	
    	return new GroupModel(id, title, notes, systemId, accountName, accountType);
	}
}