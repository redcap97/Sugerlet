package com.akr97.sugerlet;

import java.util.ArrayList;

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

import com.akr97.sugerlet.contacts.*;
import com.akr97.sugerlet.japanese.*;
import com.akr97.sugerlet.list_item.*;

public class InitialsGroupListActivity extends Activity {
	static final int MENU_ADD_CONTACT = (Menu.FIRST + 1);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_items);

		setTitle(getString(R.string.initials_group_search));
		setupInitialsIndex();
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

	public void setupInitialsIndex(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		for(char group : InitialsGroupSelector.INITIALS_GROUP_NAMES){
			String groupName = InitialsGroupSelector.getGroupName(group);
			Intent intent = InitialsGroupContactsActivity.getIntent(this, group);

			items.add(new ListContentItem(this, groupName, intent));
		}

		ListView listView = (ListView)findViewById(R.id.listView);
		TextView textView = (TextView)findViewById(R.id.emptyView);
		textView.setText(getString(R.string.message_no_group));
		listView.setEmptyView(textView);
		listView.setAdapter(new ListItemAdapter(items));
		listView.setOnItemClickListener(new ListItemClickListener(items));
	}

	private void addContact(){
		Uri uri = ContactsContract.RawContacts.CONTENT_URI;
		Intent intent = new Intent(Intent.ACTION_INSERT, uri);
		startActivityForResult(intent, 0);
	}

	public static Intent getIntent(Context context){
		return new Intent(context, InitialsGroupListActivity.class);
	}
}
