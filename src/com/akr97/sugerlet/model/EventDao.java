package com.akr97.sugerlet.model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Event;

public class EventDao extends DaoBase<EventData> {
	static final String[] PROJECTION = {
		Event.START_DATE,
		Event.TYPE,
		Event.LABEL
	};

	public EventDao(Context context) {
		super(context);
	}

	public ArrayList<EventData> get(long rawContactId){
		return readRows(getCursor(rawContactId));
	}

	public Cursor getCursor(long rawContactId){
		return getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND " 
					+ Data.MIMETYPE + "=?",
				new String[]{
					String.valueOf(rawContactId),
					Event.CONTENT_ITEM_TYPE },
				Event._ID);
	}

	@Override
	public EventData extract(Cursor c) {
		String startDate = c.getString(0);
		int type = c.getInt(1);
		String label = c.getString(2);

		return new EventData(startDate, type, label);
	}
	
	public String getTypeLabel(EventData event){
		if(hasCustomLabel(event)){
			return event.label;
		}
		return getContext().getString(Event.getTypeResource(event.type));
	}
	
	private boolean hasCustomLabel(EventData event){
		if(event.type == Event.TYPE_CUSTOM && event.label != null){
			return event.label.trim().length() > 0;
		}
		return false;
	}
}
