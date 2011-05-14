package com.akr97.sugerlet;

import java.util.Vector;
import android.util.Log;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
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
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Vector<GroupModel> groups = GroupModel.getAll(this);
        for(GroupModel group : groups){
        	Log.i(TAG, group.toString());
        }
        
        AccountChanger changer = new AccountChanger(this);
        for(AccountChanger.State s : changer){
        	Log.i(TAG, s.getName());
        	Log.i(TAG, s.getType());
        }
        
        ListView listView = (ListView)findViewById(R.id.contactList);
        listView.setAdapter(new ContactListAdapter(this));
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        			int position, long id){
        		ListView listView = (ListView)parent;
        		ContactSummaryModel item = (ContactSummaryModel)listView.getItemAtPosition(position);
        		Toast.makeText(SugerletActivity.this, item.displayName, Toast.LENGTH_LONG).show();
        	}
        });
    }
}