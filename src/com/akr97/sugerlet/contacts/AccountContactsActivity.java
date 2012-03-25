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

package com.akr97.sugerlet.contacts;

import java.util.ArrayList;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.list_item.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.util.*;

public class AccountContactsActivity extends ContactsActivity {
	private Parameter params;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.params = new Parameter();
		super.onCreate(savedInstanceState);
	}

	@Override
	public String createTitle(){
		return String.format("%s: %s",
				getString(R.string.account_search),
				AccountUtil.getHeading(params.account));
	}

	@Override
	public ArrayList<ListItem> createListItems(){
		StructuredNameDao dao = new StructuredNameDao(this);
		return createListItems(dao.getByAccount(params.account));
	}

	public static Intent getIntent(Context context, Account account){
		Intent intent = new Intent(context, AccountContactsActivity.class);
		intent.putExtra(context.getString(R.string.key_account_name), account.name);
		intent.putExtra(context.getString(R.string.key_account_type), account.type);
		return intent;
	}

	class Parameter {
		public final Intent intent;
		public final Account account;

		public Parameter(){
			this.intent = getIntent();
			this.account = new Account(getAccountName(), getAccountType());
		}

		private String getAccountName(){
			String accountName = intent.getStringExtra(getString(R.string.key_account_name));
			if(accountName == null){
				throw new RuntimeException("AccountName is not found.");
			}
			return accountName;
		}

		private String getAccountType(){
			String accountType = intent.getStringExtra(getString(R.string.key_account_type));
			if(accountType == null){
				throw new RuntimeException("AccountType is not found.");
			}
			return accountType;
		}
	}
}
