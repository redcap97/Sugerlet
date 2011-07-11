package com.akr97.sugerlet;

import java.util.ArrayList;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.akr97.sugerlet.account_state.*;
import com.akr97.sugerlet.contacts.*;
import com.akr97.sugerlet.list_item.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.util.*;

public class GroupListActivity extends Activity {
	static final int MENU_ADD_CONTACT = (Menu.FIRST + 1);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_items);

		setTitle(getString(R.string.group_search));
		setupGroupList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add(Menu.NONE, MENU_ADD_CONTACT, Menu.NONE, getString(R.string.menu_add_contact));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case MENU_ADD_CONTACT:
			addContact();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setupGroupList(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		AccountStateManager manager = AccountStateManagerFactory.create(this);
		for(AccountState state : manager.getEnabledStates()){
			items.add(getHeaderItem(state.getAccount()));
			items.add(getNoGroupItem(state.getAccount()));

			GroupDao dao = new GroupDao(this);
			ArrayList<GroupData> groups = dao.getByAccount(state.getName(), state.getType());
			for(GroupData group : groups){
				items.add(getGroupItem(state.getAccount(), group));
			}
		}

		ListView listView = (ListView)findViewById(R.id.listView);
		TextView textView = (TextView)findViewById(R.id.emptyView);
		textView.setText(getString(R.string.message_no_group));
		listView.setEmptyView(textView);
		listView.setAdapter(new ListItemAdapter(items));
		listView.setOnItemClickListener(new ListItemClickListener(items));
	}

	private ListItem getHeaderItem(Account account){
		String title = AccountUtil.getHeading(account);
		return new ListHeaderItem(this, title);
	}

	private ListItem getNoGroupItem(Account account){
		String content = getString(R.string.no_group_with_mark);
		Intent intent = GroupContactsActivity.getIntentNoGroup(this, account);
		return new ListContentItem(this, content, intent);
	}

	private ListItem getGroupItem(Account account, GroupData group){
		Intent intent = GroupContactsActivity.getIntent(this, account, group.id);
		return new ListContentItem(this, group.getDisplayName(), intent);
	}

	private void addContact(){
		Uri uri = ContactsContract.RawContacts.CONTENT_URI;
		Intent intent = new Intent(Intent.ACTION_INSERT, uri);
		startActivityForResult(intent, 0);
	}

	public static Intent getIntent(Context context){
		return new Intent(context, GroupListActivity.class);
	}
}
