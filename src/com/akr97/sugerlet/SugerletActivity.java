package com.akr97.sugerlet;

import java.util.ArrayList;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.content.Intent;

import com.akr97.sugerlet.listitem.*;

public class SugerletActivity extends Activity {
	static final int MENU_SETTING_ACCOUNT = (Menu.FIRST + 1);
	static final int MENU_ABOUT = (Menu.FIRST + 2);

	static final String SUGERLET_URL = "http://akr97.com/capsule/";
	static final Uri SUGERLET_URI = Uri.parse(SUGERLET_URL);

	final static String TAG = "com.akr97.com.sugerlet.SugerActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_items);

		setupLauncherList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_SETTING_ACCOUNT, Menu.NONE, getString(R.string.menu_setting_account));
		menu.add(Menu.NONE, MENU_ABOUT, Menu.NONE, getString(R.string.menu_about));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case MENU_SETTING_ACCOUNT:
			launchSettingAccount();
			return true;
		case MENU_ABOUT:
			launchAbout();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setupLauncherList(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		items.add(new ListHeaderItem(this,
				getString(R.string.header_menu)));
		items.add(new ListIntentItem(this,
				getString(R.string.menu_all_contacts),
				AllContactsActivity.getIntent(this)));
		items.add(new ListIntentItem(this,
				getString(R.string.menu_group_list),
				GroupListActivity.getIntent(this)));
		items.add(new ListIntentItem(this,
				getString(R.string.menu_initials_group_list),
				InitialsGroupListActivity.getIntent(this)));
		items.add(new ListIntentItem(this,
				getString(R.string.menu_starred_contacts),
				StarredContactsActivity.getIntent(this)));
		items.add(new ListIntentItem(this,
				getString(R.string.menu_account_list),
				AccountListActivity.getIntent(this)));

		items.add(new ListHeaderItem(this,
				getString(R.string.header_setting)));
		items.add(new ListIntentItem(this,
				getString(R.string.menu_setting_account),
				SettingAccountActivity.getIntent(this)));
		items.add(new ListIntentItem(this,
				getString(R.string.menu_about),
				getAboutIntent()));

		ListView listView = (ListView)findViewById(R.id.contactList);
		listView.setAdapter(new ListItemAdapter(items));
		View emptyView = findViewById(R.id.emptyView);
		listView.setEmptyView(emptyView);
		listView.setOnItemClickListener(new ListItemClickListener(items));
	}

	private Intent getAboutIntent(){
		return new Intent(Intent.ACTION_VIEW, SUGERLET_URI);
	}

	private void launchAbout(){
		startActivity(getAboutIntent());
	}

	private void launchSettingAccount(){
		startActivity(SettingAccountActivity.getIntent(this));
	}
}
