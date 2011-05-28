package com.akr97.sugerlet.model;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Email;

public class EmailModel extends ModelBase<EmailData> {
	private Context ctx;
	
	static final String[] PROJECTION = new String[]{
		Email.DATA1, //ADDRESS
		Email.TYPE,
		Email.LABEL
    };
	
	public EmailModel(Context ctx){
		this.ctx = ctx;
	}
	
	public Vector<EmailData> get(long rawContactId){
		return readRows(getCursor(rawContactId));
	}
	
	public Cursor getCursor(long rawContactId){
		return ctx.getContentResolver().query(Email.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND " 
					+ Data.MIMETYPE + "=?",
				new String[]{
					String.valueOf(rawContactId),
					Email.CONTENT_ITEM_TYPE },
				Email._ID);
	}
	
	@Override
	public EmailData extract(Cursor c) {
		String address = c.getString(0);
		int type = c.getInt(1);
		String label = c.getString(2);
		
		return new EmailData(address, type, label);
	}
}
