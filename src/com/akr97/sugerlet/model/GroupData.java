package com.akr97.sugerlet.model;

public class GroupData {
	public long id;
	public String title;
	public String notes;
	public String systemId;
	public String accountName;
	public String accountType;
	
	public GroupData(long id, String title, String notes, String systemId, 
			String accountName, String accountType){
		this.id = id;
		this.title = title;
		this.notes = notes;
		this.systemId = systemId;
		this.accountName = accountName;
		this.accountType = accountType;
	}
	
	@Override
	public String toString(){
		return String.format("id: %d, title: %s, notes: %s, systemId: %s,"
					+ " accountName: %s, accountType: %s", 
				this.id, this.title, this.notes, this.systemId,
				this.accountName, this.accountType);
	}
}
