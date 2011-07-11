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
