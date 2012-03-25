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

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.japanese.*;
import com.akr97.sugerlet.list_item.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.util.*;

public abstract class ContactsActivity extends Activity {
	private ArrayList<ListItem> items;
	private ListItemAdapter listAdapter;

	static final int MENU_ADD_CONTACT = (Menu.FIRST + 1);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_items);

		items = createListItems();
		listAdapter = new ListItemAdapter(items);

		setTitle(createTitle());
		setupContactList();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		resetContactList();
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

	public void setupContactList(){
		ListView listView = (ListView)findViewById(R.id.listView);
		listView.setAdapter(listAdapter);
		View emptyView = findViewById(R.id.emptyView);
		listView.setEmptyView(emptyView);
		listView.setOnItemClickListener(new ListItemClickListener(items));
	}

	private void resetContactList(){
		ArrayList<ListItem> changedItems = createListItems();
		items.clear();
		items.addAll(changedItems);
		listAdapter.notifyDataSetChanged();
	}

	protected ArrayList<ListItem> createListItems(ArrayList<StructuredNameData> structuredNames){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		NormalizedNameList list = NormalizedNameList.fromPhoneticNames(structuredNames);
		for(char group : InitialsGroupSelector.INITIALS_GROUP_NAMES){
			NormalizedNameList groupList = list.filter(group);
			if(groupList.size() > 0){
				items.add(new ListHeaderItem(this, InitialsGroupSelector.getGroupName(group)));
				for(NormalizedName name : groupList.sort()){
					items.add(new ContactsContentItem(this, name.getEntity()));
				}
			}
		}
		return items;
	}

	private void addContact(){
		Uri uri = ContactsContract.RawContacts.CONTENT_URI;
		Intent intent = new Intent(Intent.ACTION_INSERT, uri);
		startActivityForResult(intent, 0);
	}

	public abstract String createTitle();
	public abstract ArrayList<ListItem> createListItems();
}
