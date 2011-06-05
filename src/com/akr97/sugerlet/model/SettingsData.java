package com.akr97.sugerlet.model;

public class SettingsData {
	public final String accountName;
	public final String accountType;
	public final int ungroupedVisible;
	
	public SettingsData(String accountName, String accountType, int ungroupedVisible){
		this.accountName = accountName;
		this.accountType = accountType;
		this.ungroupedVisible = ungroupedVisible;
	}
	
	@Override
	public String toString(){
		return String.format("accountName: %s, accountType: %s, ungroupedVisible: %d",
				accountName, accountType, ungroupedVisible);
	}
}
