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

import android.accounts.Account;

public class SettingsData {
	public final String accountName;
	public final String accountType;
	public final int ungroupedVisible;

	public SettingsData(String accountName, String accountType, int ungroupedVisible){
		this.accountName = accountName;
		this.accountType = accountType;
		this.ungroupedVisible = ungroupedVisible;
	}

	public Account getAccount(){
		return new Account(accountName, accountType);
	}

	@Override
	public String toString(){
		return String.format("accountName: %s, accountType: %s, ungroupedVisible: %d",
				accountName, accountType, ungroupedVisible);
	}
}
