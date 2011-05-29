package com.akr97.sugerlet.model;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Nickname;

public class NicknameModel extends ModelBase<NicknameData> {
	private Context ctx;
	
	static final String[] PROJECTION = new String[]{
		Nickname.NAME,
		Nickname.TYPE,
		Nickname.LABEL
    };
	
	public NicknameModel(Context ctx){
		this.ctx = ctx;
	}
	
	public Vector<NicknameData> get(long rawContactId){
		return readRows(getCursor(rawContactId));
	}
	
	public Cursor getCursor(long rawContactId){
		return ctx.getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND " 
					+ Data.MIMETYPE + "=?",
	   			new String[] {
					String.valueOf(rawContactId),
					Nickname.CONTENT_ITEM_TYPE},
				Data._ID);
	}
	
	@Override
	public NicknameData extract(Cursor c) {
		String name = c.getString(0);
		int type = c.getInt(1);
		String label = c.getString(1);
		
		return new NicknameData(name, type, label);
	}
}
