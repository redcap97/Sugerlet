package com.akr97.sugerlet;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.content.Intent;

public class ContactListActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setTitle(getString(R.string.group) + " " + getGroupTitle());
		
		setListAdapter(new ContactListAdapter(this));
	}
	
	public long getGroupId(){
		Intent intent = getIntent();
		long groupId = intent.getLongExtra(getString(R.string.key_of_group_id), -1);
		if(groupId == -1L){
			throw new RuntimeException("GroupId is not found.");
		}
		return groupId;
	}
	
	public String getGroupTitle(){
		Intent intent = getIntent();
		String groupTitle = intent.getStringExtra(getString(R.string.key_of_group_title));
		if(groupTitle == null){
			throw new RuntimeException("GroupTitle is not found.");
		}
		return groupTitle;
	}
}
