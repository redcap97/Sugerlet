package com.akr97.sugerlet;

import java.util.ArrayList;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;

import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.util.AccountUtil;

public class AccountContactsGetter extends ContactsGetter {
	private final Parameter params;
	private final Intent intent;

	public AccountContactsGetter(Context context, Intent intent) {
		super(context);
		this.intent = intent;
		this.params = new Parameter();
	}

	@Override
	public String getTitle() {		
		return String.format("%s: %s",
				context.getString(R.string.account),
				AccountUtil.getHeading(params.account));
	}

	@Override
	public ArrayList<StructuredNameData> getStructuredNames() {
		StructuredNameModel model = new StructuredNameModel(context);
		return model.getByAccount(params.account.name, params.account.type);
	}
	
	public static Intent getIntent(Context context, Account account){
		Intent intent = new Intent(context, ContactListActivity.class);
		intent.putExtra(context.getString(R.string.key_account_name), account.name);
		intent.putExtra(context.getString(R.string.key_account_type), account.type);
		return intent;
	}
	
	class Parameter {
		public final Account account;
		
		public Parameter(){
			this.account = new Account(getAccountName(), getAccountType());
		}
		
		private String getAccountName(){
			String accountName = intent.getStringExtra(context.getString(R.string.key_account_name));
			if(accountName == null){
				throw new RuntimeException("AccountName is not found.");
			}
			return accountName;
		}
		
		private String getAccountType(){
			String accountType = intent.getStringExtra(context.getString(R.string.key_account_type));
			if(accountType == null){
				throw new RuntimeException("AccountType is not found.");
			}
			return accountType;
		}
	}
}
