package com.akr97.sugerlet.model;

import com.akr97.sugerlet.util.StringUtil;

import android.accounts.Account;

public class GroupData {
	public final long id;
	public final String title;
	public final String notes;
	public final String systemId;
	public final String accountName;
	public final String accountType;
	public final boolean groupVisible;
	public final boolean deleted;

	public GroupData(long id, String title, String notes, String systemId, 
			String accountName, String accountType, boolean groupVisible, boolean deleted){
		this.id = id;
		this.title = title;
		this.notes = notes;
		this.systemId = systemId;
		this.accountName = accountName;
		this.accountType = accountType;
		this.groupVisible = groupVisible;
		this.deleted = deleted;
	}

	public String getDisplayName(){
		return StringUtil.findNonEmptyElement(systemId, title);
	}

	public Account getAccount(){
		return new Account(accountName, accountType);
	}

	@Override
	public String toString(){
		return String.format("id: %d, title: %s, notes: %s, systemId: %s,"
					+ " accountName: %s, accountType: %s,"
					+ " groupVisible: %b, deleted: %b",
				this.id, this.title, this.notes, this.systemId,
				this.accountName, this.accountType,
				this.groupVisible, this.deleted);
	}
}
