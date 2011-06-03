package com.akr97.sugerlet.model;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Settings;

public class SettingsModel extends ModelBase<SettingsData> {
	private Context ctx;
	
	static final String[] PROJECTION = new String[]{
		Settings.ACCOUNT_NAME,
		Settings.ACCOUNT_TYPE,
		Settings.UNGROUPED_VISIBLE
	};
	
	public SettingsModel(Context ctx){
		this.ctx = ctx;
	}
	
	public Vector<SettingsData> getAll(){
		return readRows(getCursor());
	}
	
	public Cursor getCursor(){
		return ctx.getContentResolver().query(
				Settings.CONTENT_URI,
				PROJECTION, 
				null, null, null);
	}

	@Override
	public SettingsData extract(Cursor c) {
		String accountName = c.getString(0);
		String accountType = c.getString(1);
		int ungroupedVisible = c.getInt(2);
		
		return new SettingsData(accountName, accountType, ungroupedVisible);
	}
}