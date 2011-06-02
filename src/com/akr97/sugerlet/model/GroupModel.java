package com.akr97.sugerlet.model;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Groups;

import com.akr97.sugerlet.*;

public class GroupModel extends ModelBase<GroupData> {
	private Context ctx;
	
	static final String TAG = "com.akr97.sugerlet.GroupModel";
	
	static final String[] PROJECTION = new String[]{
    	Groups._ID,
    	Groups.TITLE,
    	Groups.NOTES,
    	Groups.SYSTEM_ID,
    	Groups.ACCOUNT_NAME,
    	Groups.ACCOUNT_TYPE
    };
	
	public GroupModel(Context ctx){
		this.ctx = ctx;
	}
	
	@Override
	public GroupData extract(Cursor c){
       	long id = c.getLong(0);
    	String title = c.getString(1);
    	String notes = c.getString(2);
    	String systemId = c.getString(3);
    	String accountName = c.getString(4);
    	String accountType = c.getString(5);
    	
    	return new GroupData(id, title, notes, systemId, accountName, accountType);
	}
	
	public Vector<GroupData> get(){
		Vector<GroupData> groups;
		AccountStateManager accountChanger = AccountStateManagerFactory.create(ctx);
		if(accountChanger.hasFilters()){
			groups = new Vector<GroupData>();
			for(AccountStateManager.State s : accountChanger){
				if(s.isEnabled()){
					groups.addAll(getByAccount(s.getName(), s.getType()));
				}
			}
		}else{
			groups = getAll();
		}
		
		return groups;
	}
	
	public Vector<GroupData> getAll(){
        return readRows(getCursor());
	}

	public Vector<GroupData> getByAccount(String accountName, String accountType){
		return readRows(getCursor(accountName, accountType));
	}
	
	public GroupData getById(long id){
		return readRow(getCursor(id));
	}

	public Cursor getCursor(){
		return ctx.getContentResolver().query(Groups.CONTENT_URI, PROJECTION, null, null, null);
	}
	
	public Cursor getCursor(String accountName, String accountType){
		return ctx.getContentResolver().query(Groups.CONTENT_URI,
				PROJECTION, Groups.ACCOUNT_NAME + "=? AND " + Groups.ACCOUNT_TYPE + "=?", 
				new String[]{ accountName, accountType }, Groups._ID);
	}
	
	public Cursor getCursor(long groupId){
		return ctx.getContentResolver().query(Groups.CONTENT_URI,
				PROJECTION, Groups._ID + "=?", 
				new String[]{ String.valueOf(groupId) }, null);
	}
}