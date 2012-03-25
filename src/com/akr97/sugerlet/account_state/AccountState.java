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

package com.akr97.sugerlet.account_state;

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
