package com.akr97.sugerlet.model;

import java.util.Vector;

import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;
import android.provider.ContactsContract.RawContactsEntity;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.Data;
import android.content.Context;
import android.database.Cursor;
//import android.util.Log;

import com.akr97.sugerlet.AccountStateManager;
import com.akr97.sugerlet.AccountStateManagerFactory;
import com.akr97.sugerlet.util.CursorJoinerWithIntKey;

public class StructuredNameModel extends ModelBase<StructuredNameData> {
    static final String TAG = "com.akr97.sugerlet.model.StructuredNameModel";
	
	static final String[] PROJECTION = new String[] {
		RawContactsEntity._ID,
		StructuredName.DISPLAY_NAME,
		StructuredName.GIVEN_NAME,
		StructuredName.FAMILY_NAME,
		StructuredName.PHONETIC_GIVEN_NAME,
		StructuredName.PHONETIC_FAMILY_NAME
	};

	public StructuredNameModel(Context ctx){
		super(ctx);
	}
	
	@Override
	public StructuredNameData extract(Cursor c){
		long rawContactId = c.getLong(0);
		String displayName = c.getString(1);
		String givenName = c.getString(2);
		String familyName = c.getString(3);
		String phoneticGivenName = c.getString(4);
		String phoneticFamilyName = c.getString(5);
		
		return new StructuredNameData(rawContactId, displayName, givenName, familyName,
				phoneticGivenName, phoneticFamilyName);
	}
	
	public StructuredNameData getByRawContactsId(long id){
		return readRow(getCursorByRawContactId(id));
	}

	public Vector<StructuredNameData> getFromGroup(long groupId){
		GroupModel model = new GroupModel(getContext());
		GroupData group = model.getById(groupId);
		
        Cursor c1 = getCursorBelongToGroup(groupId);
        Cursor c2 = getCursorBelongToAccount(group.accountName, group.accountType);

        CursorJoinerWithIntKey cursorJoiner = new CursorJoinerWithIntKey(c2, new String[]{ RawContactsEntity._ID },
        		c1, new String[]{ Data.RAW_CONTACT_ID });

        Vector<StructuredNameData> results = new Vector<StructuredNameData>();
        for(CursorJoinerWithIntKey.Result r : cursorJoiner){
        	switch(r){
        	case BOTH:
        		results.add(extract(c2));
        	}
        }
        return results;
	}
	
	public Vector<StructuredNameData> getNoGroup(String accountName, String accountType){
		Cursor c1 = getCursorBelongToGroup(accountName, accountType);
		Cursor c2 = getCursorBelongToAccount(accountName, accountType);
		
		CursorJoinerWithIntKey cursorJoiner = new CursorJoinerWithIntKey(c2, new String[]{ RawContactsEntity._ID },
				c1, new String[]{ RawContactsEntity._ID });
		
		Vector<StructuredNameData> results = new Vector<StructuredNameData>();
		for(CursorJoinerWithIntKey.Result r : cursorJoiner){
			switch(r){
			case LEFT:
				results.add(extract(c2));
			}
		}
		return results;
	}
	
	public Vector<StructuredNameData> getNoGroup(){
		Vector<StructuredNameData> results = new Vector<StructuredNameData>();
		AccountStateManager changer = AccountStateManagerFactory.create(getContext());
		for(AccountStateManager.State state : changer){
			if(state.isEnabled()){
				results.addAll(getNoGroup(state.getName(), state.getType()));
			}
		}
		results.addAll(getNoAccount());
		return results;
	}
	
	public Vector<StructuredNameData> getNoAccount(){
		return readRows(getCursorBelongToNoAccount());
	}
	
	private Cursor getCursorBelongToAccount(String accountName, String accountType){
        return getContentResolver().query(RawContactsEntity.CONTENT_URI, 
                PROJECTION,
                RawContactsEntity.MIMETYPE + "=? AND " 
                	+ RawContacts.ACCOUNT_NAME + "=? AND " 
                	+ RawContacts.ACCOUNT_TYPE + "=?",
                new String[]{
        			StructuredName.CONTENT_ITEM_TYPE,
        			accountName,
        			accountType}, 
                RawContactsEntity._ID);
	}
	
	private Cursor getCursorBelongToNoAccount(){
		return getContentResolver().query(RawContactsEntity.CONTENT_URI,
				PROJECTION,
				RawContactsEntity.MIMETYPE + "=? AND " +
					RawContacts.ACCOUNT_NAME + " IS NULL AND " +
					RawContacts.ACCOUNT_TYPE + " IS NULL",	
				new String[]{ StructuredName.CONTENT_ITEM_TYPE }, 
				RawContacts._ID);
	}
	
	private Cursor getCursorBelongToGroup(String accountName, String accountType){
		return getContentResolver().query(RawContactsEntity.CONTENT_URI,
				new String[]{ RawContactsEntity._ID },
				RawContactsEntity.MIMETYPE + "=? AND " +
					RawContacts.ACCOUNT_NAME + "=? AND " +
					RawContacts.ACCOUNT_TYPE + "=?",
				new String[]{
					GroupMembership.CONTENT_ITEM_TYPE,
					accountName,
					accountType},
				RawContactsEntity._ID);
	}
	
	private Cursor getCursorBelongToGroup(long groupId){
		return getContentResolver().query(Data.CONTENT_URI,
                new String[]{ Data.RAW_CONTACT_ID },
                Data.MIMETYPE + "=? AND " +
                	GroupMembership.GROUP_ROW_ID + "=?", 
                new String[]{
        			GroupMembership.CONTENT_ITEM_TYPE,
        			String.valueOf(groupId)},
                Data.RAW_CONTACT_ID);
	}
	
	private Cursor getCursorByRawContactId(long rawContactId){
		return getContentResolver().query(RawContactsEntity.CONTENT_URI,
				PROJECTION,
				RawContactsEntity.MIMETYPE + "=? AND "
					+ RawContactsEntity._ID + "=?",
				new String[]{
					StructuredName.CONTENT_ITEM_TYPE,
					String.valueOf(rawContactId)},
				RawContactsEntity._ID);
	}
}