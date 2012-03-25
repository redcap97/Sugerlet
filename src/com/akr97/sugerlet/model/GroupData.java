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
