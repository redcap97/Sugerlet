package com.akr97.sugerlet.model;

public class RawContactsData {
	public final long id;
	public final long contactId;
	public final boolean starred;
	public final String accountName;
	public final String accountType;

	public RawContactsData(long id, long contactId, boolean starred,
			String accountName, String accountType){
		this.id = id;
		this.contactId = contactId;
		this.starred = starred;
		this.accountName = accountName;
		this.accountType = accountType;
	}

	@Override
	public String toString(){
		return String.format("id: %d, contactId: %d, starred: %b," +
				" accountName: %s, accountType: %s",
				id, contactId, starred, accountName, accountType);
	}
}
