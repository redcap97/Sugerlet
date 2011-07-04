package com.akr97.sugerlet.model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Email;

public class EmailDao extends DaoBase<EmailData> {
	static final String[] PROJECTION = {
		Email.DATA,
		Email.TYPE,
		Email.LABEL
	};

	public EmailDao(Context ctx){
		super(ctx);
	}

	public EmailData getFirst(long rawContactId){
		return readRow(getCursor(rawContactId));
	}

	public ArrayList<EmailData> get(long rawContactId){
		return readRows(getCursor(rawContactId));
	}

	public Cursor getCursor(long rawContactId){
		return getContentResolver().query(Data.CONTENT_URI,
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
	
	public String getTypeLabel(EmailData email){
		return Email.getTypeLabel(getResources(), email.type, email.label).toString();
	}
}
