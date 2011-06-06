package com.akr97.sugerlet;

import java.util.Vector;

import com.akr97.sugerlet.model.GroupData;
import com.akr97.sugerlet.model.GroupModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GroupListActivity extends Activity {
	private Vector<GroupData> groups;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.add(getString(R.string.no_group));

        GroupModel model = new GroupModel(this);
        this.groups = model.get();
        for(GroupData group : groups){
        	adapter.add(group.title);
        }
        
        ListView listView = (ListView)findViewById(R.id.contactList);
        listView.setAdapter(adapter);
        View emptyView = findViewById(R.id.emptyView);
        listView.setEmptyView(emptyView);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        			int position, long id){
    			Intent intent = null;
        		if(position == 0){
        			intent = getGroupListIntent(ContactListActivity.NO_GROUP_ID, getString(R.string.no_group));
        		}else{
        			GroupData group = groups.get(position-1);
        			intent = getGroupListIntent(group.id, group.title);
        		}
       			startActivity(intent);
        	}
        });
	}
	
	private Intent getContactListIntent(){
		return new Intent(this, ContactListActivity.class);
	}
	
	private Intent getGroupListIntent(long groupId, String title){
		Intent intent = getContactListIntent();
		intent.putExtra(getString(R.string.key_of_group_id), groupId);
		intent.putExtra(getString(R.string.key_of_group_title), title);
		return intent;
	}
}
