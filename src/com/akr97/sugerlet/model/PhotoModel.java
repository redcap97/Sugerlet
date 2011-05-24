package com.akr97.sugerlet.model;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.Data;

public class PhotoModel extends ModelBase<PhotoData> {
	private Context ctx;
	
	static final String[] PROJECTION = new String[]{
		Photo._ID,
		Photo.PHOTO
	};
	
	public PhotoModel(Context ctx){
		this.ctx = ctx;
	}
	
	@Override
	public PhotoData extract(Cursor c){
		long id = c.getLong(0);
		byte[] bytes = c.getBlob(1);
		
		return new PhotoData(id, bytes);
	}
	
	public PhotoData getByRawContactId(long rawContactId){
		return readRow(getCursor(rawContactId));
	}
	
	public Cursor getCursor(long rawContactId){
		return ctx.getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "= ? AND " +
					Data.MIMETYPE + "=?",
				new String[] {
					String.valueOf(rawContactId),
					Photo.CONTENT_ITEM_TYPE},
				null);
	}
}
