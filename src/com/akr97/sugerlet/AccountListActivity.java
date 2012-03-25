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
import com.akr97.sugerlet.list_item.*;

public class AccountListActivity extends Activity {
	static final int MENU_ADD_CONTACT = (Menu.FIRST + 1);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_items);

		setTitle(getString(R.string.account_search));
		setupAccountList();
	}

	public void setupAccountList(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		AccountStateManager manager = AccountStateManagerFactory.create(this);

		for(AccountState state : manager.getEnabledStates()){
			items.add(new AccountListItem(this, state.getAccount()));
		}
		ListView listView = (ListView)findViewById(R.id.listView);
		TextView textView = (TextView)findViewById(R.id.emptyView);
		textView.setText(getString(R.string.message_no_account));
		listView.setEmptyView(textView);
		listView.setAdapter(new ListItemAdapter(items));
		listView.setOnItemClickListener(new ListItemClickListener(items));
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

	private void addContact(){
		Uri uri = ContactsContract.RawContacts.CONTENT_URI;
		Intent intent = new Intent(Intent.ACTION_INSERT, uri);
		startActivityForResult(intent, 0);
	}

	public static Intent getIntent(Context context){
		return new Intent(context, AccountListActivity.class);
	}
}
