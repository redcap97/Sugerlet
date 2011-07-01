package com.akr97.sugerlet;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.akr97.sugerlet.listitem.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.util.*;

public class GroupListActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_items);

		setTitle(getString(R.string.group));
		setupGroupList();
	}

	private void setupGroupList(){        	
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		AccountStateManager manager = AccountStateManagerFactory.create(this);
		for(AccountState state : manager.getEnabledStates()){
			items.add(new ListHeaderItem(this, AccountUtil.getHeading(state.getAccount())));
			items.add(new ListIntentItem(this, getString(R.string.no_group),
					GroupContactsActivity.getIntentNoGroup(this, state.getAccount())));

			GroupDao dao = new GroupDao(this);
			ArrayList<GroupData> groups = dao.getByAccount(state.getName(), state.getType());
			for(GroupData group : groups){
				items.add(new ListIntentItem(this, group.title,
						GroupContactsActivity.getIntent(this, state.getAccount(), group.id)));
			}
		}

		ListView listView = (ListView)findViewById(R.id.listView);
		TextView textView = (TextView)findViewById(R.id.emptyView);
		textView.setText(getString(R.string.message_no_group));
		listView.setEmptyView(textView);
		listView.setAdapter(new ListItemAdapter(items));
		listView.setOnItemClickListener(new ListItemClickListener(items));
	}

	public static Intent getIntent(Context context){
		return new Intent(context, GroupListActivity.class);
	}
}
