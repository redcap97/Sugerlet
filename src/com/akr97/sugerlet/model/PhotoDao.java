package com.akr97.sugerlet.model;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.Data;

public class PhotoDao extends DaoBase<PhotoData> {
	static final String[] PROJECTION = {
		Photo.PHOTO
	};

	public PhotoDao(Context ctx){
		super(ctx);
	}

	@Override
	public PhotoData extract(Cursor c){
		byte[] bytes = c.getBlob(0);
		return new PhotoData(bytes);
	}

	public PhotoData getByRawContactId(long rawContactId){
		PhotoData result = readRow(getCursor(rawContactId));
		if(result == null){
			return new MissingPhotoData(getContext());
		}
		return result;
	}

	public Cursor getCursor(long rawContactId){
		return getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "= ? AND " +
					Data.MIMETYPE + "=?",
				new String[] {
					String.valueOf(rawContactId),
					Photo.CONTENT_ITEM_TYPE},
				Photo._ID);
	}
}
