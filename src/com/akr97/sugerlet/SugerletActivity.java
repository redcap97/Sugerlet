package com.akr97.sugerlet;

import java.util.Vector;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.akr97.sugerlet.model.*;

public class SugerletActivity extends Activity {
	static final int MENU_SETTING_ACCOUNT = (Menu.FIRST + 1);
	static final int MENU_ABOUT = (Menu.FIRST + 2);
	
	static final String SUGERLET_URL = "http://akr97.com/capsule/";
	static final Uri SUGERLET_URI = Uri.parse(SUGERLET_URL);
		
	final static String TAG = "com.akr97.com.sugerlet.SugerActivity";
	private Vector<GroupData> groups;
	
    /** Called when the activity is first created. */
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
    			Intent intent = new Intent(parent.getContext(), ContactListActivity.class);
        		if(position == 0){
        			intent.putExtra(getString(R.string.key_of_group_id), ContactListActivity.NO_GROUP_ID);
        			intent.putExtra(getString(R.string.key_of_group_title), getString(R.string.no_group));       			
        		}else{
        			GroupData group = groups.get(position-1);
        			intent.putExtra(getString(R.string.key_of_group_id), group.id);
        			intent.putExtra(getString(R.string.key_of_group_title), group.title);
        		}
       			startActivity(intent);
        	}
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(Menu.NONE, MENU_SETTING_ACCOUNT, Menu.NONE, getString(R.string.menu_setting_account));
    	menu.add(Menu.NONE, MENU_ABOUT, Menu.NONE, getString(R.string.menu_about));
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	case MENU_SETTING_ACCOUNT:
    		doSettingAccount();
    		return true;
    	case MENU_ABOUT:
    		doAbout();
    		return true;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    private void doAbout(){
        Intent intent = new Intent(Intent.ACTION_VIEW, SUGERLET_URI);
        startActivity(intent);
    }
    
    private void doSettingAccount(){
		Intent intent = new Intent(this, SettingAccountActivity.class);
		startActivity(intent);
    }
}
