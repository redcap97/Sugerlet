package com.akr97.sugerlet.model;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;

public class GroupMembershipModel extends ModelBase<GroupData> {
	private Context ctx;
	private GroupModel groupModel;
	
	static final String[] PROJECTION = new String[]{
		GroupMembership.GROUP_ROW_ID
    };
	
	public GroupMembershipModel(Context ctx){
		this.ctx = ctx;
		this.groupModel = new GroupModel(ctx);
	}
	
	public Vector<GroupData> get(long rawContactId){
		return readRows(getCursor(rawContactId));
	}
	
	public Cursor getCursor(long rawContactId){
		return ctx.getContentResolver().query(Data.CONTENT_URI,
				PROJECTION,
				Data.RAW_CONTACT_ID + "=? AND " 
					+ Data.MIMETYPE + "=?",
				new String[]{
					String.valueOf(rawContactId),
					GroupMembership.CONTENT_ITEM_TYPE },
				GroupMembership._ID);
	}

	@Override
	public GroupData extract(Cursor c) {
		long groupRowId = c.getLong(0);
		return groupModel.getById(groupRowId);
	}
}
