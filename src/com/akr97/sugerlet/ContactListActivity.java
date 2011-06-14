package com.akr97.sugerlet;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;

import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.profile.*;

public class ContactListActivity extends Activity {
	private ContactsGetter getter;
	private ArrayList<StructuredNameData> structuredNames;
	
	public static final long NO_GROUP_ID = GroupContactsGetter.NO_GROUP_ID;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		setType();
		this.structuredNames = getter.getStructuredNames();
		setTitle(getter.getTitle());
		setupContactList();
	}
	
	public void setupContactList(){
		ListView listView = (ListView)findViewById(R.id.contactList);
		listView.setAdapter(new ContactListAdapter(this, structuredNames));
		
		View emptyView = findViewById(R.id.emptyView);
		listView.setEmptyView(emptyView);
		
        listView.setOnItemClickListener(new ItemClickListener());
	}
	
	private void setType(){
		//ContactsGetter getter = new GroupContactsGetter(this, getIntent());
		this.getter = new StarredContactsGetter(this);
	}

	class ItemClickListener implements AdapterView.OnItemClickListener{
		@Override
    	public void onItemClick(AdapterView<?> parent, View view,
    			int position, long id){
    		StructuredNameData structuredName = structuredNames.get(position);
    		
    		Intent intent = new Intent(parent.getContext(), ProfileActivity.class);
			intent.putExtra(getString(R.string.key_raw_contact_id), structuredName.rawContactId);
    		startActivity(intent);
		}
	}
}
