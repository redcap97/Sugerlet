package com.akr97.sugerlet.model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Note;

public class NoteDao extends DaoBase<NoteData> {
	static final String[] PROJECTION = {
		Note.NOTE
	};

	public NoteDao(Context context) {
		super(context);
	}
	
	public ArrayList<NoteData> get(long rawContactId){
		return readRows(getCursor(rawContactId));
	}

	public Cursor getCursor(long rawContactId){
		return getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND " 
					+ Data.MIMETYPE + "=?",
	   			new String[] {
					String.valueOf(rawContactId),
					Note.CONTENT_ITEM_TYPE},
				Note._ID);
	}
	
	@Override
	public NoteData extract(Cursor c) {
		String note = c.getString(0);
		return new NoteData(note);
	}
}
