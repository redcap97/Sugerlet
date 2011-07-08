package com.akr97.sugerlet.contacts;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_contacts);

		this.items = createListItems();
		this.listAdapter = new ListItemAdapter(items);

		setTitle(getString(R.string.search_contacts));
		setupHeader();
		setupContactList();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		resetContactList();
	}

	public void setupHeader(){
		EditText editText = (EditText)findViewById(R.id.editor);
		editText.addTextChangedListener(new TextChangedListener());
	}

	public void setupContactList(){
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

	public ArrayList<ListItem> createListItems(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		StructuredNameDao dao = new StructuredNameDao(this);
		NormalizedNameList list = NormalizedNameList.from(dao.getAll());
		for(NormalizedName name : list.filter(pattern).sort()){
			items.add(new ContactsContentItem(this, name.getEntity()));
		}
		return items;
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
