package com.akr97.sugerlet;

import android.accounts.Account;

public class AccountState {
	private final Account account;
	private boolean enabled;

	public AccountState(Account account, boolean enabled){
		this.account = account;
		this.enabled = enabled;
	}

	public AccountState(AccountState state){
		this.account = new Account(state.getName(), state.getType());
		this.enabled = state.isEnabled();
	}

	public String getName(){
		return account.name;
	}

	public String getType(){
		return account.type;
	}

	public Account getAccount(){
		return account;
	}

	public boolean isEnabled(){
		return enabled;
	}

	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}

	@Override
	public String toString(){
		return String.format("name: %s, type: %s, enabled: %b",
				account.name, account.type, enabled);
	}
}
