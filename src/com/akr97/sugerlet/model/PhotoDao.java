/*
Copyright (C) 2012 Akira Midorikawa

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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
			return new MissingPhotoData(context);
		}
		return result;
	}

	public Cursor getCursor(long rawContactId){
		return getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "= ? AND " +
					Data.MIMETYPE + "=? AND " +
					Photo.PHOTO + " IS NOT NULL",
				new String[] {
					String.valueOf(rawContactId),
					Photo.CONTENT_ITEM_TYPE},
				Photo._ID);
	}
}
