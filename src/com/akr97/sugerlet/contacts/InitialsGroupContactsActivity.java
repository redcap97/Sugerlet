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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.japanese.*;
import com.akr97.sugerlet.list_item.*;
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
		String groupName = InitialsGroupSelector.getGroupName(params.initialsGroup);
		return String.format("%s: %s", getString(R.string.initials_group_search), groupName);
	}

	@Override
	public ArrayList<ListItem> createListItems(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		StructuredNameDao dao = new StructuredNameDao(this);
		NormalizedNameList list = NormalizedNameList.fromPhoneticNames(dao.getAll())
									.filter(params.initialsGroup).sort();

		char currentGroup = '\0';
		InitialsSubgroupSelector subgroupSelector = new InitialsSubgroupSelector();
		for(NormalizedName name : list){
			char subgroup = subgroupSelector.select(name.get());
			if(currentGroup != subgroup){
				currentGroup = subgroup;

				String subgroupName = InitialsSubgroupSelector.getGroupName(currentGroup);
				items.add(new ListHeaderItem(this, subgroupName));
			}
			items.add(new ContactsContentItem(this, name.getEntity()));
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
