package com.akr97.sugerlet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class SettingAccountActivity extends Activity {
	static final int MENU_SAVE = (Menu.FIRST + 1);
	static final int MENU_RESET = (Menu.FIRST + 2);
	static final int DURATION_TOAST = 5;
	
	private SettingAccountListAdapter listAdapter;
	private ListView listView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_account);
        
        listAdapter = new SettingAccountListAdapter(this);
		listView = (ListView)findViewById(R.id.list);
		listView.setAdapter(listAdapter);
		View emptyView = findViewById(R.id.emptyView);
		listView.setEmptyView(emptyView);
		
		View saveButton = findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				saveChanges();
			}
		});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(Menu.NONE, MENU_SAVE, Menu.NONE, getString(R.string.menu_save));
    	menu.add(Menu.NONE, MENU_RESET, Menu.NONE, getString(R.string.menu_reset));
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	case MENU_SAVE:
    		saveChanges();
    		return true;
    	case MENU_RESET:
    		resetChanges();
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    }
    
    public void saveChanges(){
		listAdapter.save();
		Toast.makeText(this, getString(R.string.message_save), DURATION_TOAST).show();
		finish();
    }
    
    public void resetChanges(){
		listAdapter.reset();
		listAdapter.notifyDataSetChanged();
		listView.invalidateViews();
    }
}
