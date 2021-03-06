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
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.list_item.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.util.*;

public class SearchContactsActivity extends Activity {
	private String pattern = null;

	private ArrayList<ListItem> items;
	private ListItemAdapter listAdapter;

	private static final int SEARCH_IMAGE_DIP = 30;
	private static final int SEARCH_IMAGE_RES_ID = android.R.drawable.ic_search_category_default;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_contacts);

		this.items = createListItems();
		this.listAdapter = new ListItemAdapter(items);

		setTitle(getString(R.string.keyword_search));
		setupHeader();
		setupContactList();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		resetContactList();
	}

	private void setupHeader(){
		EditText editText = (EditText)findViewById(R.id.editor);

		int lengthImage = convertDipIntoPx(SEARCH_IMAGE_DIP);
		Drawable searchImage = getResources().getDrawable(SEARCH_IMAGE_RES_ID);

		searchImage.setBounds(0, 0, lengthImage, lengthImage);
		editText.setCompoundDrawables(null, null, searchImage, null);

		editText.addTextChangedListener(new TextChangedListener());
	}

	private void setupContactList(){
		ListView listView = (ListView)findViewById(R.id.list);
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

	private ArrayList<ListItem> createListItems(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		StructuredNameDao dao = new StructuredNameDao(this);
		NormalizedNameList list = NormalizedNameList.fromFullNames(dao.getAll());
		for(NormalizedName name : list.filter(pattern).sort()){
			items.add(new ContactsContentItem(this, name.getEntity()));
		}
		return items;
	}

	private int convertDipIntoPx(int dip){
		float scale = getResources().getDisplayMetrics().density;
		return (int)(dip * scale + 0.5f);
	}

	public static Intent getIntent(Context context){
		return new Intent(context, SearchContactsActivity.class);
	}

	class TextChangedListener implements TextWatcher{
		@Override
		public void afterTextChanged(Editable s) {
			pattern = s.toString();
			resetContactList();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
	}
}
