package com.akr97.sugerlet;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.akr97.sugerlet.japanese.*;
import com.akr97.sugerlet.listitem.*;

public class InitialsGroupListActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_items);

		setTitle(getString(R.string.initials_group));
		setupInitialsIndex();
	}

	public void setupInitialsIndex(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		for(char group : InitialsGroupSelector.INITIALS_GROUP_NAMES){
			items.add(new ListIntentItem(this,
					InitialsGroupSelector.getGroupName(group),
					InitialsGroupContactsActivity.getIntent(this, group)));
		}

		ListView listView = (ListView)findViewById(R.id.listView);
		TextView textView = (TextView)findViewById(R.id.emptyView);
		textView.setText(getString(R.string.message_no_group));
		listView.setEmptyView(textView);
		listView.setAdapter(new ListItemAdapter(items));
		listView.setOnItemClickListener(new ListItemClickListener(items));
	}

	public static Intent getIntent(Context context){
		return new Intent(context, InitialsGroupListActivity.class);
	}
}
