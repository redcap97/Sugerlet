package com.akr97.sugerlet.model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Website;

public class WebsiteDao extends ModelBase<WebsiteData> {
	static final String[] PROJECTION = new String[]{
		Website.URL,
		Website.TYPE,
		Website.LABEL
    };
	
	public WebsiteDao(Context ctx){
		super(ctx);
	}
	
	public ArrayList<WebsiteData> get(long rawContactId){
		return readRows(getCursor(rawContactId));
	}
	
	public Cursor getCursor(long rawContactId){
		return getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND " 
					+ Data.MIMETYPE + "=?",
				new String[]{
					String.valueOf(rawContactId),
					Website.CONTENT_ITEM_TYPE },
				Website._ID);
	}
	
	@Override
	public WebsiteData extract(Cursor c) {
		String url = c.getString(0);
		int type = c.getInt(1);
		String label = c.getString(2);
		
		return new WebsiteData(url, type, label);
	}

}
