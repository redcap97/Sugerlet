package com.akr97.sugerlet.contacts;

import java.util.ArrayList;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.list_item.*;
import com.akr97.sugerlet.model.*;

public class GroupContactsActivity extends ContactsActivity {
	public static final long NO_GROUP_ID = 0L;

	private Parameter params;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.params = new Parameter();
		super.onCreate(savedInstanceState);
	}

	@Override
	public String createTitle() {
		return String.format("%s: %s",
				getString(R.string.group),
				getGroupName(params.groupId));
	}

	@Override
	public ArrayList<ListItem> createListItems() {
		return createListItems(getStructuredNames());
	}

	public ArrayList<StructuredNameData> getStructuredNames(){
		StructuredNameDao dao = new StructuredNameDao(this);
		if(params.groupId == NO_GROUP_ID){
			return dao.getNoGroup(params.account);
		}else{
			return dao.getFromGroup(params.groupId);
		}
	}

	public static Intent getIntent(Context context, Account account, long groupId){
		Intent intent = new Intent(context, GroupContactsActivity.class);
		intent.putExtra(context.getString(R.string.key_account_name), account.name);
		intent.putExtra(context.getString(R.string.key_account_type), account.type);
		intent.putExtra(context.getString(R.string.key_group_id), groupId);
		return intent;
	}

	public static Intent getIntentNoGroup(Context context, Account account){
		return getIntent(context, account, NO_GROUP_ID);
	}

	private String getGroupName(long groupId){
		if(groupId == NO_GROUP_ID){
			return getString(R.string.no_group);
		}

		GroupDao groupDao = new GroupDao(this);
		GroupData group = groupDao.getById(groupId);
		return group.title;
	}

	class Parameter{
		public final Intent intent;
		public final Account account;
		public final long groupId;

		public Parameter(){
			this.intent = getIntent();
			this.groupId = getGroupId();
			this.account = new Account(getAccountName(), getAccountType());
		}

		private long getGroupId(){
			long groupId = intent.getLongExtra(getString(R.string.key_group_id), -1);
			if(groupId == -1L){
				throw new RuntimeException("GroupId is not found.");
			}
			return groupId;
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
