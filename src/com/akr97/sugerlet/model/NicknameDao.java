package com.akr97.sugerlet.model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Nickname;

public class NicknameDao extends DaoBase<NicknameData> {
	static final String[] PROJECTION = {
		Nickname.NAME,
		Nickname.TYPE,
		Nickname.LABEL
	};

	public NicknameDao(Context ctx){
		super(ctx);
	}

	public ArrayList<NicknameData> get(long rawContactId){
		return readRows(getCursor(rawContactId));
	}

	public Cursor getCursor(long rawContactId){
		return getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND " 
					+ Data.MIMETYPE + "=?",
	   			new String[] {
					String.valueOf(rawContactId),
					Nickname.CONTENT_ITEM_TYPE},
				Nickname._ID);
	}

	@Override
	public NicknameData extract(Cursor c) {
		String name = c.getString(0);
		int type = c.getInt(1);
		String label = c.getString(1);

		return new NicknameData(name, type, label);
	}
}
