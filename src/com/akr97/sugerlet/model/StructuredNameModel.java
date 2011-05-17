package com.akr97.sugerlet.model;

import java.util.Vector;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;
import android.provider.ContactsContract.RawContactsEntity;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.util.Log;

import com.akr97.sugerlet.AccountStateManager;
import com.akr97.sugerlet.AccountStateManagerFactory;
import com.akr97.sugerlet.util.CursorJoinerWithIntKey;
import com.akr97.sugerlet.util.StringUtil;

public class StructuredNameModel {
	public long rawContactId;
	public String displayName;
	public String givenName;
	public String familyName;
	public String phoneticGivenName;
	public String phoneticFamilyName;
	
    static final String TAG = "com.akr97.sugerlet.model.StructuredNameModel";
	
	static final String[] PROJECTION = new String[] {
		RawContactsEntity._ID,
		StructuredName.DISPLAY_NAME,
		StructuredName.GIVEN_NAME,
		StructuredName.FAMILY_NAME,
		StructuredName.PHONETIC_GIVEN_NAME,
		StructuredName.PHONETIC_FAMILY_NAME
	};
	
	public StructuredNameModel(long rawContactId, String displayName, String givenName, String familyName,
			String phoneticGivenName, String phoneticFamilyName){
		this.rawContactId = rawContactId;
		this.displayName = displayName;
		this.givenName = givenName;
		this.familyName = familyName;
		this.phoneticGivenName = phoneticGivenName;
		this.phoneticFamilyName = phoneticFamilyName;
	}
	
	public String getName(){
		return getJapaneseStyleName(familyName, givenName);
	}
	
	public String getPhoneticName(){
		return getJapaneseStyleName(phoneticFamilyName, phoneticGivenName);
	}

	public static Vector<StructuredNameModel> getFromGroup(Context ctx, long groupId){
		GroupModel group = GroupModel.getById(ctx, groupId);
		
        Cursor c1 = ctx.getContentResolver().query(Data.CONTENT_URI,
                new String[]{ Data.RAW_CONTACT_ID },
                Data.MIMETYPE + "=? AND " +
                	GroupMembership.GROUP_ROW_ID + "=?", 
                new String[]{
        			GroupMembership.CONTENT_ITEM_TYPE,
        			String.valueOf(groupId)},
                Data.RAW_CONTACT_ID);
        
        Cursor c2 = ctx.getContentResolver().query(RawContactsEntity.CONTENT_URI, 
                PROJECTION,
                RawContactsEntity.MIMETYPE + "=? AND " 
                	+ RawContacts.ACCOUNT_NAME + "=? AND " 
                	+ RawContacts.ACCOUNT_TYPE + "=?",
                new String[]{
        			StructuredName.CONTENT_ITEM_TYPE,
        			group.accountName,
        			group.accountType}, 
                RawContactsEntity._ID);

        CursorJoinerWithIntKey cursorJoiner = new CursorJoinerWithIntKey(c2, new String[]{ RawContactsEntity._ID },
        		c1, new String[]{ Data.RAW_CONTACT_ID });

        Vector<StructuredNameModel> results = new Vector<StructuredNameModel>();
        for(CursorJoinerWithIntKey.Result r : cursorJoiner){
        	switch(r){
        	case BOTH:
        		results.add(extractObjectFromCursor(c2));
        	}
        }
        return results;
	}
	
	public static Vector<StructuredNameModel> getFromNoGroup(Context ctx, String accountName, String accountType){
		Vector<StructuredNameModel> results = new Vector<StructuredNameModel>();
		
		Cursor c1 = ctx.getContentResolver().query(RawContactsEntity.CONTENT_URI,
				new String[]{ RawContactsEntity._ID },
				RawContactsEntity.MIMETYPE + "=? AND " +
				RawContacts.ACCOUNT_NAME + "=? AND " +
				RawContacts.ACCOUNT_TYPE + "=?",
				new String[]{
				GroupMembership.CONTENT_ITEM_TYPE,
				accountName,
				accountType},
				RawContactsEntity._ID);

		Cursor c2 = ctx.getContentResolver().query(RawContactsEntity.CONTENT_URI, 
				PROJECTION,
				RawContactsEntity.MIMETYPE + "=? AND " +
				RawContacts.ACCOUNT_NAME + "=? AND " +
				RawContacts.ACCOUNT_TYPE + "=?",	
				new String[]{
				StructuredName.CONTENT_ITEM_TYPE,
				accountName,
				accountType}, 
				RawContacts._ID);

		CursorJoinerWithIntKey cursorJoiner = new CursorJoinerWithIntKey(c2, new String[]{ RawContactsEntity._ID },
				c1, new String[]{ RawContactsEntity._ID });
		
		for(CursorJoinerWithIntKey.Result r : cursorJoiner){
			switch(r){
			case LEFT:
				results.add(extractObjectFromCursor(c2));
			}
		}
		return results;
	}
	
	public static Vector<StructuredNameModel> getFromNoGroup(Context ctx){
		Vector<StructuredNameModel> results = new Vector<StructuredNameModel>();
		AccountStateManager changer = AccountStateManagerFactory.create(ctx);
		for(AccountStateManager.State state : changer){
			if(state.isEnabled()){
				results.addAll(getFromNoGroup(ctx, state.getName(), state.getType()));
			}
		}
		return results;
	}
	
	public static StructuredNameModel extractObjectFromCursor(Cursor c){
		long rawContactId = c.getLong(0);
		String displayName = c.getString(1);
		String givenName = c.getString(2);
		String familyName = c.getString(3);
		String phoneticGivenName = c.getString(4);
		String phoneticFamilyName = c.getString(5);
		
		return new StructuredNameModel(rawContactId, displayName, givenName, familyName,
				phoneticGivenName, phoneticFamilyName);
	}
	
	private static String getJapaneseStyleName(String familyName, String givenName){
		if(givenName == null){
			return StringUtil.toNonNullString(familyName);
		}else if(familyName == null){
			return StringUtil.toNonNullString(givenName);
		}else{
			return familyName + " " + givenName;
		}
	}
}