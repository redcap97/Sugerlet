package com.akr97.sugerlet;

import java.util.Vector;
import android.util.Log;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/*
import android.database.*;
import android.provider.*;
import android.provider.ContactsContract.*;
import android.provider.ContactsContract.CommonDataKinds.*;
import android.accounts.*;
*/

import com.akr97.sugerlet.model.*;

public class SugerletActivity extends Activity {
	final static String TAG = "com.akr97.com.sugerlet.SugerActivity";
	private Vector<GroupModel> groups;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.add(getString(R.string.no_group));
        
        /*
        AccountStateManager changer = AccountStateManagerFactory.create(this);
        for(AccountStateManager.State s : changer){
        	if(s.getName().equals("redcap97@gmail.com")){ s.disable(); }
        }
        */

        this.groups = GroupModel.get(this);
        for(GroupModel group : groups){
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
    			Intent intent = new Intent(parent.getContext(), ContactListActivity.class);
        		if(position == 0){
        			intent.putExtra(getString(R.string.key_of_group_id), ContactListActivity.NO_GROUP_ID);
        			intent.putExtra(getString(R.string.key_of_group_title), getString(R.string.no_group));       			
        		}else{
        			GroupModel group = groups.get(position-1);
        			intent.putExtra(getString(R.string.key_of_group_id), group.id);
        			intent.putExtra(getString(R.string.key_of_group_title), group.title);
        		}
       			startActivity(intent);
        	}
        });
    }
}