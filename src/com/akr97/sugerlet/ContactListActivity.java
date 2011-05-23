package com.akr97.sugerlet;

import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.akr97.sugerlet.model.*;

public class ContactListActivity extends Activity {
	public static final long NO_GROUP_ID = 0L;
	private Vector<StructuredNameModel> structureNames;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Parameter params = new Parameter();
		setTitle(getString(R.string.group) + ": " + params.groupTitle);
		
		ListView listView = (ListView)findViewById(R.id.contactList);
		this.structureNames = getStructuredNames(params.groupId);
		listView.setAdapter(new ContactListAdapter(this, structureNames));
		
		View emptyView = findViewById(R.id.emptyView);
		listView.setEmptyView(emptyView);
		
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        			int position, long id){
        		StructuredNameModel structuredName = structureNames.get(position);
        		
        		Intent intent = new Intent(parent.getContext(), ProfileActivity.class);
    			intent.putExtra(getString(R.string.key_of_raw_contact_id), structuredName.rawContactId);
        		startActivity(intent);
        	}
        });
	}
	
	Vector<StructuredNameModel> getStructuredNames(long groupId){
		if(groupId == NO_GROUP_ID){
			return StructuredNameModel.getNoGroup(this);
		}else{
			return StructuredNameModel.getFromGroup(this, groupId);
		}
	}
	
	class Parameter{
		private Intent intent;
		
		public long groupId;
		public String groupTitle;
		
		public Parameter(){
			this.intent = getIntent();
			this.groupId = getGroupId();
			this.groupTitle = getGroupTitle();
		}
		
		private long getGroupId(){
			long groupId = intent.getLongExtra(getString(R.string.key_of_group_id), -1);
			if(groupId == -1L){
				throw new RuntimeException("GroupId is not found.");
			}
			return groupId;
		}
		
		private String getGroupTitle(){
			String groupTitle = intent.getStringExtra(getString(R.string.key_of_group_title));
			if(groupTitle == null){
				throw new RuntimeException("GroupTitle is not found.");
			}
			return groupTitle;
		}
	}
}
