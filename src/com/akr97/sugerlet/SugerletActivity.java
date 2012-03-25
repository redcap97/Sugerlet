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

package com.akr97.sugerlet;

import java.util.ArrayList;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.content.Intent;

import com.akr97.sugerlet.contacts.*;
import com.akr97.sugerlet.list_item.*;

public class SugerletActivity extends Activity {
	static final int MENU_SETTING_ACCOUNT = (Menu.FIRST + 1);
	static final int MENU_ADD_CONTACT = (Menu.FIRST + 2);

	static final String SUGERLET_URL = "http://akr97.com/capsule/?tag=sugerlet";
	static final Uri SUGERLET_URI = Uri.parse(SUGERLET_URL);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_items);

		setupLauncherList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_SETTING_ACCOUNT, Menu.NONE, getString(R.string.menu_setting_account));
		menu.add(Menu.NONE, MENU_ADD_CONTACT, Menu.NONE, getString(R.string.menu_add_contact));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case MENU_SETTING_ACCOUNT:
			launchSettingAccount();
			return true;
		case MENU_ADD_CONTACT:
			addContact();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setupLauncherList(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		items.addAll(getContactItems());
		items.addAll(getSearchItems());
		items.addAll(getOtherItems());

		ListView listView = (ListView)findViewById(R.id.listView);
		listView.setAdapter(new ListItemAdapter(items));
		View emptyView = findViewById(R.id.emptyView);
		listView.setEmptyView(emptyView);
		listView.setOnItemClickListener(new ListItemClickListener(items));
	}

	private ArrayList<ListItem> getContactItems(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		items.add(new ListHeaderItem(this,
				getString(R.string.header_contact)));

		items.add(new ListContentItem(this,
				getString(R.string.menu_all_contacts),
				AllContactsActivity.getIntent(this)));
		items.add(new ListContentItem(this,
				getString(R.string.menu_starred_contacts),
				StarredContactsActivity.getIntent(this)));

		return items;
	}

	private ArrayList<ListItem> getSearchItems(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		items.add(new ListHeaderItem(this,
				getString(R.string.header_search)));

		items.add(new ListContentItem(this,
				getString(R.string.menu_group_search),
				GroupListActivity.getIntent(this)));
		items.add(new ListContentItem(this,
				getString(R.string.menu_keyword_search),
				SearchContactsActivity.getIntent(this)));
		items.add(new ListContentItem(this,
				getString(R.string.menu_initials_group_search),
				InitialsGroupListActivity.getIntent(this)));
		items.add(new ListContentItem(this,
				getString(R.string.menu_account_search),
				AccountListActivity.getIntent(this)));

		return items;
	}

	private ArrayList<ListItem> getOtherItems(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		items.add(new ListHeaderItem(this,
				getString(R.string.header_other)));

		items.add(new ListContentItem(this,
				getString(R.string.menu_telephone),
				new Intent(Intent.ACTION_DIAL, null)));
		items.add(new ListContentItem(this,
				getString(R.string.menu_setting_account),
				SettingAccountActivity.getIntent(this)));
		items.add(new ListContentItem(this,
				getString(R.string.menu_about),
				getAboutIntent()));

		return items;
	}

	private Intent getAboutIntent(){
		return new Intent(Intent.ACTION_VIEW, SUGERLET_URI);
	}

	private void launchSettingAccount(){
		startActivity(SettingAccountActivity.getIntent(this));
	}

	private void addContact(){
		Uri uri = ContactsContract.RawContacts.CONTENT_URI;
		Intent intent = new Intent(Intent.ACTION_INSERT, uri);
		startActivityForResult(intent, 0);
	}
}
