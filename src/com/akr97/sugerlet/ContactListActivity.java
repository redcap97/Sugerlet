package com.akr97.sugerlet;

import java.util.Vector;

import android.accounts.Account;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;

import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.profile.*;

public class ContactListActivity extends Activity {
	private Vector<StructuredNameData> structureNames;
	
	public static final long NO_GROUP_ID = 0L;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Parameter params = new Parameter();
		setTitle(getString(R.string.group) + ": " + getGroupName(params.groupId));
	
		ListView listView = (ListView)findViewById(R.id.contactList);
		this.structureNames = getStructuredNames(params);
		listView.setAdapter(new ContactListAdapter(this, structureNames));
		
		View emptyView = findViewById(R.id.emptyView);
		listView.setEmptyView(emptyView);
		
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        			int position, long id){
        		StructuredNameData structuredName = structureNames.get(position);
        		
        		Intent intent = new Intent(parent.getContext(), ProfileActivity.class);
    			intent.putExtra(getString(R.string.key_of_raw_contact_id), structuredName.rawContactId);
        		startActivity(intent);
        	}
        });
	}
	
	private String getGroupName(long groupId){
		if(groupId == NO_GROUP_ID){
			return getString(R.string.no_group);
		}
		
		GroupModel groupModel = new GroupModel(this);
		GroupData group = groupModel.getById(groupId);
		return group.title;
	}
	
	private Vector<StructuredNameData> getStructuredNames(Parameter params){
		StructuredNameModel model = new StructuredNameModel(this);
		if(params.groupId == NO_GROUP_ID){
			return model.getNoGroup(params.account.name, params.account.type);
		}else{
			return model.getFromGroup(params.groupId);
		}
	}
	
	class Parameter{
		private final Intent intent;
		
		public final Account account;
		public final long groupId;
		
		public Parameter(){
			this.intent = getIntent();
			this.groupId = getGroupId();
			this.account = new Account(getAccountName(), getAccountType());
		}
		
		private long getGroupId(){
			long groupId = intent.getLongExtra(getString(R.string.key_of_group_id), -1);
			if(groupId == -1L){
				throw new RuntimeException("GroupId is not found.");
			}
			return groupId;
		}
		
		private String getAccountName(){
			String accountName = intent.getStringExtra(getString(R.string.key_of_account_name));
			if(accountName == null){
				throw new RuntimeException("AccountName is not found.");
			}
			return accountName;
		}
		
		private String getAccountType(){
			String accountType = intent.getStringExtra(getString(R.string.key_of_account_type));
			if(accountType == null){
				throw new RuntimeException("AccountType is not found.");
			}
			return accountType;
		}
	}
}
