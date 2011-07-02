package com.akr97.sugerlet.model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;

public class StructuredPostalDao extends DaoBase<StructuredPostalData> {
	static final String TAG = "com.akr97.sugerlet.model.PhoneModel";

	static final String[] PROJECTION = {
		StructuredPostal.FORMATTED_ADDRESS,
		StructuredPostal.TYPE,
		StructuredPostal.LABEL,
		StructuredPostal.STREET,
		StructuredPostal.POBOX,
		StructuredPostal.NEIGHBORHOOD,
		StructuredPostal.CITY,
		StructuredPostal.REGION,
		StructuredPostal.POSTCODE,
		StructuredPostal.COUNTRY
	};

	public StructuredPostalDao(Context ctx){
		super(ctx);
	}

	public ArrayList<StructuredPostalData> get(long rawContactId){
		return readRows(getCursor(rawContactId));
	}

	public Cursor getCursor(long rawContactId){
		return getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND " 
					+ Data.MIMETYPE + "=?",
				new String[] {
					String.valueOf(rawContactId),
					StructuredPostal.CONTENT_ITEM_TYPE},
				StructuredPostal._ID);
	}

	@Override
	public StructuredPostalData extract(Cursor c) {
		String formattedAddress = c.getString(0);
		int type = c.getInt(1);
		String label = c.getString(2);
		String street = c.getString(3);
		String pobox = c.getString(4);
		String neighborhood = c.getString(5);
		String city = c.getString(6);
		String region = c.getString(7);
		String postcode = c.getString(8);
		String country = c.getString(9);

		return new StructuredPostalData(formattedAddress, type, label, 
				street, pobox, neighborhood, city, region, postcode, country);
	}
	
	public String getTypeLabel(StructuredPostalData postal){
		return StructuredPostal.getTypeLabel(getResources(), 
				postal.type, postal.label).toString();
	}
}
