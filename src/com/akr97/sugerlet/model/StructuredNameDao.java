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

import java.util.ArrayList;

import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;
import android.provider.ContactsContract.RawContactsEntity;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.Data;
import android.accounts.Account;
import android.content.Context;
import android.database.Cursor;

import com.akr97.sugerlet.account_state.*;
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

		AccountStateManager manager = AccountStateManagerFactory.create(context);
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

		AccountStateManager manager = AccountStateManagerFactory.create(context);
		for(AccountState state : manager.getEnabledStates()){
			results.addAll(getStarred(state.getAccount()));
		}
		return results;
	}

	public ArrayList<StructuredNameData> getStarred(Account account){
		return readRows(getCursorStarred(account));
	}

	public ArrayList<StructuredNameData> getFromGroup(long groupId){
		ArrayList<StructuredNameData> results = new ArrayList<StructuredNameData>();

		GroupDao dao = new GroupDao(context);
		GroupData group = dao.getById(groupId);

		Cursor c1 = getCursorBelongToGroup(groupId);
		Cursor c2 = getCursorBelongToAccount(group.getAccount());
		try{
			CursorJoinerWithLongKey cursorJoiner = new CursorJoinerWithLongKey(
					c2, new String[]{ RawContactsEntity._ID },
					c1, new String[]{ Data.RAW_CONTACT_ID });

			for(CursorJoinerWithLongKey.Result r : cursorJoiner){
				switch(r){
				case BOTH:
					results.add(extract(c2));
				}
			}
		}finally{
			c1.close();
			c2.close();
		}
		return results;
	}

	public ArrayList<StructuredNameData> getByAccount(Account account){
		return readRows(getCursorBelongToAccount(account));
	}

	public ArrayList<StructuredNameData> getNoGroup(Account account){
		ArrayList<StructuredNameData> results = new ArrayList<StructuredNameData>();

		Cursor c1 = getCursorBelongToGroup(account);
		Cursor c2 = getCursorBelongToAccount(account);
		try{
			CursorJoinerWithLongKey cursorJoiner = new CursorJoinerWithLongKey(
					c2, new String[]{ RawContactsEntity._ID },
					c1, new String[]{ RawContactsEntity._ID });

			for(CursorJoinerWithLongKey.Result r : cursorJoiner){
				switch(r){
				case LEFT:
					results.add(extract(c2));
				}
			}
		}finally{
			c1.close();
			c2.close();
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
					RawContacts.ACCOUNT_TYPE + "=? AND " +
					RawContacts.DELETED + "=?",
				new String[]{
					StructuredName.CONTENT_ITEM_TYPE,
					account.name, account.type,
					String.valueOf(0)},
				RawContactsEntity._ID);
	}

	private Cursor getCursorBelongToNoAccount(){
		return getContentResolver().query(RawContactsEntity.CONTENT_URI,
				PROJECTION,
				RawContactsEntity.MIMETYPE + "=? AND " +
					RawContacts.ACCOUNT_NAME + " IS NULL AND " +
					RawContacts.ACCOUNT_TYPE + " IS NULL AND " +
					RawContacts.DELETED + "=?",
				new String[]{
					StructuredName.CONTENT_ITEM_TYPE,
					String.valueOf(0)
				},
				RawContacts._ID);
	}

	private Cursor getCursorBelongToGroup(Account account){
		return getContentResolver().query(RawContactsEntity.CONTENT_URI,
				new String[]{ RawContactsEntity._ID },
				RawContactsEntity.MIMETYPE + "=? AND " +
					RawContacts.ACCOUNT_NAME + "=? AND " +
					RawContacts.ACCOUNT_TYPE + "=? AND " +
					RawContacts.DELETED + "=?",
				new String[]{
					GroupMembership.CONTENT_ITEM_TYPE,
					account.name, account.type,
					String.valueOf(0)},
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
				RawContactsEntity.MIMETYPE + "=? AND " +
					RawContactsEntity._ID + "=?",
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
					RawContacts.ACCOUNT_TYPE + "=? AND " +
					RawContacts.DELETED + "=?",
				new String[]{
					String.valueOf(1),
					StructuredName.CONTENT_ITEM_TYPE,
					account.name, account.type,
					String.valueOf(0)},
				RawContacts._ID);
	}
}