package com.akr97.sugerlet;

import java.util.Vector;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;
import android.util.Log;
import com.akr97.sugerlet.model.*;

public class ContactListActivity extends Activity {
	public static final long NO_GROUP_ID = 0L;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Parameter group = new Parameter();
		setTitle(getString(R.string.group) + ": " + group.title);
		
		Vector<StructuredNameModel> structuredNames;
		if(group.id == NO_GROUP_ID){
			structuredNames = StructuredNameModel.getFromNoGroup(this);
		}else{
			structuredNames = StructuredNameModel.get(this, group.id);
		}

		ListView listView = (ListView)findViewById(R.id.contactList);
		listView.setAdapter(new ContactListAdapter(this, structuredNames));
	}
	
	class Parameter{
		private Intent intent;
		
		public long id;
		public String title;
		
		public Parameter(){
			this.intent = getIntent();
			setup();
		}
		
		public void setup(){
			this.id = getGroupId();
			this.title = getGroupTitle();
		}
		
		public long getGroupId(){
			long groupId = intent.getLongExtra(getString(R.string.key_of_group_id), -1);
			if(groupId == -1L){
				throw new RuntimeException("GroupId is not found.");
			}
			return groupId;
		}
		
		public String getGroupTitle(){
			String groupTitle = intent.getStringExtra(getString(R.string.key_of_group_title));
			if(groupTitle == null){
				throw new RuntimeException("GroupTitle is not found.");
			}
			return groupTitle;
		}
	}
}
