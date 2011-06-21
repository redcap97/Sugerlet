package com.akr97.sugerlet;

import java.util.ArrayList;

import android.accounts.Account;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Context;
import android.content.Intent;

import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.profile.*;

public class ContactListActivity extends Activity {
	private ContactsGetter getter;
	private ArrayList<StructuredNameData> structuredNames;
	
	public static final int TYPE_GROUP = 1;
	public static final int TYPE_STARRED = 2;
	public static final int TYPE_ACCOUNT = 3;
	
	private static final long NO_GROUP_ID = GroupContactsGetter.NO_GROUP_ID;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Parameter params = new Parameter();
		setType(params.type);
		
		this.structuredNames = getter.getStructuredNames();
		setTitle(getter.getTitle());
		setupContactList();
	}
	
	public void setupContactList(){
		ListView listView = (ListView)findViewById(R.id.contactList);
		listView.setAdapter(new ContactListAdapter(this, structuredNames));
		
		View emptyView = findViewById(R.id.emptyView);
		listView.setEmptyView(emptyView);
		
        listView.setOnItemClickListener(new ItemClickListener());
	}
	
	private void setType(int type){
		switch(type){
		case TYPE_GROUP:
			this.getter = new GroupContactsGetter(this, getIntent());
			break;
		case TYPE_ACCOUNT:
			this.getter = new AccountContactsGetter(this, getIntent());
			break;
		case TYPE_STARRED:
			this.getter = new StarredContactsGetter(this); 
			break;
		}
	}
	
	public static Intent getIntentStarred(Context context){
    	Intent intent = new Intent(context, ContactListActivity.class);
    	intent.putExtra(context.getString(R.string.key_type), TYPE_STARRED);
    	return intent;
	}
	
	public static Intent getIntentGroup(Context context, Account account, long groupId){
		Intent intent = GroupContactsGetter.getIntent(context, account, groupId);
		intent.putExtra(context.getString(R.string.key_type), TYPE_GROUP);
		return intent;
	}
	
	public static Intent getIntentNoGroup(Context context, Account account){
		return ContactListActivity.getIntentGroup(context, account, NO_GROUP_ID);
	}
	
	public static Intent getIntentAccount(Context context, Account account){
		Intent intent = AccountContactsGetter.getIntent(context, account);
		intent.putExtra(context.getString(R.string.key_type), TYPE_ACCOUNT);
		return intent;
	}

	class Parameter {
		private Intent intent;
		
		public int type;
		
		public Parameter(){
			this.intent = getIntent();
			this.type = getType();
		}
		
		private int getType(){
			int type = intent.getIntExtra(getString(R.string.key_type), -1);
			if(type == -1){
				throw new RuntimeException("Type is not found.");
			}
			return type;
		}
	}
	
	class ItemClickListener implements AdapterView.OnItemClickListener{
		@Override
    	public void onItemClick(AdapterView<?> parent, View view,
    			int position, long id){
    		StructuredNameData structuredName = structuredNames.get(position);
    		startActivity(ProfileActivity.getIntent(parent.getContext(), structuredName.rawContactId));
		}
	}
}
