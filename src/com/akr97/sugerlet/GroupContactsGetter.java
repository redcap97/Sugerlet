package com.akr97.sugerlet;

import java.util.ArrayList;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;

import com.akr97.sugerlet.model.*;

public class GroupContactsGetter {
	private Context context;
	private Intent intent;
	private Parameter params;
	
	public GroupContactsGetter(Context context, Intent intent){
		this.context = context;
		this.intent = intent;
		this.params = new Parameter();
	}
	
	public String getTitle(){
		return context.getString(R.string.group) + ": " + getGroupName(params.groupId);
	}
	
	public ArrayList<StructuredNameData> getStructuredNames(){
		StructuredNameModel model = new StructuredNameModel(context);
		if(params.groupId == ContactListActivity.NO_GROUP_ID){
			return model.getNoGroup(params.account.name, params.account.type);
		}else{
			return model.getFromGroup(params.groupId);
		}
	}
	
	private String getGroupName(long groupId){
		if(groupId == ContactListActivity.NO_GROUP_ID){
			return context.getString(R.string.no_group);
		}
		
		GroupModel groupModel = new GroupModel(context);
		GroupData group = groupModel.getById(groupId);
		return group.title;
	}
	
	class Parameter{
		public final Account account;
		public final long groupId;
		
		public Parameter(){
			this.groupId = getGroupId();
			this.account = new Account(getAccountName(), getAccountType());
		}
		
		private long getGroupId(){
			long groupId = intent.getLongExtra(context.getString(R.string.key_group_id), -1);
			if(groupId == -1L){
				throw new RuntimeException("GroupId is not found.");
			}
			return groupId;
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
