package com.akr97.sugerlet;

import java.util.ArrayList;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.akr97.sugerlet.listitem.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.util.*;

public class GroupListActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setupGroupList();
	}
	
	private void setupGroupList(){        	
        ArrayList<ListItem> items = new ArrayList<ListItem>();
        AccountStateManager manager = AccountStateManagerFactory.create(this);
        for(AccountStateManager.State state : manager.getEnabledStates()){
        	items.add(new ListHeaderItem(this, AccountUtil.getHeading(state.getAccount())));
        	items.add(new ListIntentItem(this, getString(R.string.no_group),
        			getNoGroupContactListIntent(state.getAccount())));

        	GroupModel model = new GroupModel(this);
        	ArrayList<GroupData> groups = model.getByAccount(state.getName(), state.getType());
        	for(GroupData group : groups){
        		items.add(new ListIntentItem(this, group.title,
        				getContactListIntent(state.getAccount(), group.id)));
        	}
        }
        
        ListView listView = (ListView)findViewById(R.id.contactList);
        TextView textView = (TextView)findViewById(R.id.emptyView);
        textView.setText(getString(R.string.message_no_group));
        listView.setEmptyView(textView);
        listView.setAdapter(new ListItemAdapter(items));
        listView.setOnItemClickListener(new ItemClickListener(items));
	}
	
	private Intent getContactListIntent(){
		Intent intent = new Intent(this, ContactListActivity.class);
		intent.putExtra(getString(R.string.key_type), ContactListActivity.TYPE_GROUP);
		return intent;
	}
	
	private Intent getContactListIntent(Account account, long groupId){
		Intent intent = getContactListIntent();
		intent.putExtra(getString(R.string.key_account_name), account.name);
		intent.putExtra(getString(R.string.key_account_type), account.type);
		intent.putExtra(getString(R.string.key_group_id), groupId);
		return intent;
	}
	
	private Intent getNoGroupContactListIntent(Account account){
		return getContactListIntent(account, ContactListActivity.NO_GROUP_ID);
	}
	
	static class ItemClickListener implements AdapterView.OnItemClickListener {
		private final ArrayList<ListItem> items;
		
		public ItemClickListener(ArrayList<ListItem> items){
			this.items = items;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ListItem item = items.get(position);
			item.onClick(view);
		}
	}
}
