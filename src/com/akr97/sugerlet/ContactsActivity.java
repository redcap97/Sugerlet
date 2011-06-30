package com.akr97.sugerlet;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.akr97.sugerlet.japanese.*;
import com.akr97.sugerlet.listitem.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.util.*;

public abstract class ContactsActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_items);
		setTitle(createTitle());
		setupContactList();
	}

	public void setupContactList(){
		ListView listView = (ListView)findViewById(R.id.contactList);

		ArrayList<ListItem> items = createListItems();
		listView.setAdapter(new ListItemAdapter(items));
		View emptyView = findViewById(R.id.emptyView);
		listView.setEmptyView(emptyView);
		listView.setOnItemClickListener(new ListItemClickListener(items));
	}

	protected ArrayList<ListItem> createListItems(ArrayList<StructuredNameData> structuredNames){
		NormalizedNameList list = NormalizedNameList.fromStructuredNames(structuredNames);
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		for(char group : InitialsGroupSelector.INITIALS_GROUP_NAMES){
			NormalizedNameList groupList = list.filter(group);
			if(groupList.size() > 0){
				items.add(new ListHeaderItem(this, InitialsGroupSelector.getGroupName(group)));
				for(NormalizedName name : groupList.sort()){
					items.add(new ContactListContentItem(this, name.getEntity()));
				}
			}
		}
		return items;
	}

	public abstract String createTitle();
	public abstract ArrayList<ListItem> createListItems();
}
