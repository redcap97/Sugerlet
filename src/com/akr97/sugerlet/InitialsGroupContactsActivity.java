package com.akr97.sugerlet;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.akr97.sugerlet.japanese.InitialsGroupSelector;
import com.akr97.sugerlet.listitem.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.util.*;

public class InitialsGroupContactsActivity extends ContactsActivity {
	private Parameter params;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.params = new Parameter();
		super.onCreate(savedInstanceState);
	}

	@Override
	public String createTitle(){
		return getString(R.string.initials_group);
	}

	@Override
	public ArrayList<ListItem> createListItems(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		StructuredNameDao dao = new StructuredNameDao(this);
		NormalizedNameList list = NormalizedNameList.fromStructuredNames(dao.getAll())
									.filter(params.initialsGroup).sort();
		if(list.size() > 0){
			items.add(new ListHeaderItem(this,
					InitialsGroupSelector.getGroupName(params.initialsGroup)));
			for(NormalizedName name : list){
				items.add(new ContactListContentItem(this, name.getEntity()));
			}
		}
		return items;
	}

	public static Intent getIntent(Context context, char initialsGroup){
		Intent intent = new Intent(context, InitialsGroupContactsActivity.class);
		intent.putExtra(context.getString(R.string.key_initials_group), initialsGroup);
		return intent;
	}

	class Parameter{
		public final Intent intent;
		public final char initialsGroup;

		public Parameter(){
			this.intent = getIntent();
			this.initialsGroup = this.getInitialsGroup();
		}

		public char getInitialsGroup(){
			char initialsGroup = intent.getCharExtra(getString(R.string.key_initials_group), '\0');
			if(initialsGroup == '\0'){
				throw new RuntimeException("InitialsGroup is not found.");
			}
			return initialsGroup;
		}
	}
}
