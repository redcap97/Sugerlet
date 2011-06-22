package com.akr97.sugerlet.model;

import android.accounts.Account;

public class GroupData {
	public final long id;
	public final String title;
	public final String notes;
	public final String systemId;
	public final String accountName;
	public final String accountType;
	
	public GroupData(long id, String title, String notes, String systemId, 
			String accountName, String accountType){
		this.id = id;
		this.title = title;
		this.notes = notes;
		this.systemId = systemId;
		this.accountName = accountName;
		this.accountType = accountType;
	}
	
	public Account getAccount(){
		return new Account(accountName, accountType);
	}
	
	@Override
	public String toString(){
		return String.format("id: %d, title: %s, notes: %s, systemId: %s,"
					+ " accountName: %s, accountType: %s", 
				this.id, this.title, this.notes, this.systemId,
				this.accountName, this.accountType);
	}
}
