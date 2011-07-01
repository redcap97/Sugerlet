package com.akr97.sugerlet.model;

import java.util.ArrayList;

import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;
import android.provider.ContactsContract.RawContactsEntity;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.Data;
import android.accounts.Account;
import android.content.Context;
import android.database.Cursor;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.util.*;

public class StructuredNameDao extends DaoBase<StructuredNameData> {
	static final String TAG = "com.akr97.sugerlet.model.StructuredNameModel";

	static final String[] PROJECTION = {
		RawContactsEntity._ID,
		StructuredName.DISPLAY_NAME,
		StructuredName.GIVEN_NAME,
		StructuredName.FAMILY_NAME,
		StructuredName.PHONETIC_GIVEN_NAME,
		StructuredName.PHONETIC_FAMILY_NAME
	};

	public StructuredNameDao(Context ctx){
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

	public ArrayList<StructuredNameData> getAll(){
		ArrayList<StructuredNameData> results = new ArrayList<StructuredNameData>();

		AccountStateManager manager = AccountStateManagerFactory.create(getContext());
		for(AccountState state : manager.getEnabledStates()){
			results.addAll(getByAccount(state.getAccount()));
		}	
		return results;
	}

	public StructuredNameData getByRawContactsId(long id){
		return readRow(getCursorByRawContactId(id));
	}

	public ArrayList<StructuredNameData> getStarred(){
		ArrayList<StructuredNameData> results = new ArrayList<StructuredNameData>();

		AccountStateManager manager = AccountStateManagerFactory.create(getContext());
		for(AccountState state : manager.getEnabledStates()){
			results.addAll(getStarred(state.getAccount()));
		}
		return results;
	}

	public ArrayList<StructuredNameData> getStarred(Account account){
		return readRows(getCursorStarred(account));
	}

	public ArrayList<StructuredNameData> getFromGroup(long groupId){
		GroupDao dao = new GroupDao(getContext());
		GroupData group = dao.getById(groupId);

		Cursor c1 = getCursorBelongToGroup(groupId);
		Cursor c2 = getCursorBelongToAccount(group.getAccount());

		CursorJoinerWithLongKey cursorJoiner = new CursorJoinerWithLongKey(c2, new String[]{ RawContactsEntity._ID },
				c1, new String[]{ Data.RAW_CONTACT_ID });

		ArrayList<StructuredNameData> results = new ArrayList<StructuredNameData>();
		for(CursorJoinerWithLongKey.Result r : cursorJoiner){
			switch(r){
			case BOTH:
				results.add(extract(c2));
			}
		}
		return results;
	}

	public ArrayList<StructuredNameData> getByAccount(Account account){
		return readRows(getCursorBelongToAccount(account));
	}

	public ArrayList<StructuredNameData> getNoGroup(Account account){
		Cursor c1 = getCursorBelongToGroup(account);
		Cursor c2 = getCursorBelongToAccount(account);

		CursorJoinerWithLongKey cursorJoiner = new CursorJoinerWithLongKey(c2, new String[]{ RawContactsEntity._ID },
				c1, new String[]{ RawContactsEntity._ID });

		ArrayList<StructuredNameData> results = new ArrayList<StructuredNameData>();
		for(CursorJoinerWithLongKey.Result r : cursorJoiner){
			switch(r){
			case LEFT:
				results.add(extract(c2));
			}
		}
		return results;
	}

	public ArrayList<StructuredNameData> getNoAccount(){
		return readRows(getCursorBelongToNoAccount());
	}

	private Cursor getCursorBelongToAccount(Account account){
		return getContentResolver().query(RawContactsEntity.CONTENT_URI,
				PROJECTION,
				RawContactsEntity.MIMETYPE + "=? AND " +
					RawContacts.ACCOUNT_NAME + "=? AND " +
					RawContacts.ACCOUNT_TYPE + "=?",
				new String[]{
					StructuredName.CONTENT_ITEM_TYPE,
					account.name,
					account.type},
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

	private Cursor getCursorBelongToGroup(Account account){
		return getContentResolver().query(RawContactsEntity.CONTENT_URI,
				new String[]{ RawContactsEntity._ID },
				RawContactsEntity.MIMETYPE + "=? AND " +
					RawContacts.ACCOUNT_NAME + "=? AND " +
					RawContacts.ACCOUNT_TYPE + "=?",
				new String[]{
					GroupMembership.CONTENT_ITEM_TYPE,
					account.name,
					account.type},
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

	private Cursor getCursorStarred(Account account){
		return getContentResolver().query(RawContactsEntity.CONTENT_URI,
				PROJECTION,
				RawContacts.STARRED + "=? AND " +
					RawContactsEntity.MIMETYPE + "=? AND " +
					RawContacts.ACCOUNT_NAME + "=? AND " +
					RawContacts.ACCOUNT_TYPE + "=?",
				new String[]{
					String.valueOf(1),
					StructuredName.CONTENT_ITEM_TYPE,
					account.name, account.type },
				RawContacts._ID);
	}
}