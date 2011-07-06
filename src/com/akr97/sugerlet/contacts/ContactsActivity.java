package com.akr97.sugerlet.contacts;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

		NormalizedNameList list = NormalizedNameList.from(structuredNames);
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

	public abstract String createTitle();
	public abstract ArrayList<ListItem> createListItems();
}
