package com.akr97.sugerlet;

import java.util.Vector;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;

public class SugerletActivity extends Activity {
	static final int MENU_SETTING_ACCOUNT = (Menu.FIRST + 1);
	static final int MENU_ABOUT = (Menu.FIRST + 2);
	
	static final String SUGERLET_URL = "http://akr97.com/capsule/";
	static final Uri SUGERLET_URI = Uri.parse(SUGERLET_URL);
		
	final static String TAG = "com.akr97.com.sugerlet.SugerActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setupLauncherList();
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
    		launchSettingAccount();
    		return true;
    	case MENU_ABOUT:
    		launchAbout();
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    }
    
    private void setupLauncherList(){
        Vector<LauncherListItem> items = new Vector<LauncherListItem>();
        items.add(new LauncherHeaderItem(this, 
        		getString(R.string.header_of_menu)));
        items.add(new LauncherIntentItem(this,
        		getString(R.string.menu_group_list),
        		getGroupListIntent()));
        
        items.add(new LauncherHeaderItem(this,
        		getString(R.string.header_of_setting)));
        items.add(new LauncherIntentItem(this,
        		getString(R.string.menu_setting_account),
        		getSettingAccountIntent()));
        items.add(new LauncherIntentItem(this,
        		getString(R.string.menu_about),
        		getAboutIntent()));
        
        ListView listView = (ListView)findViewById(R.id.contactList);
        listView.setAdapter(new LauncherListAdapter(items));
        View emptyView = findViewById(R.id.emptyView);
        listView.setEmptyView(emptyView);
        listView.setOnItemClickListener(new ItemClickListener(items));
    }
    
    private Intent getGroupListIntent(){
    	return new Intent(this, GroupListActivity.class);
    }
    
    private Intent getAboutIntent(){
        return new Intent(Intent.ACTION_VIEW, SUGERLET_URI);
    }
    
    private Intent getSettingAccountIntent(){
    	return new Intent(this, SettingAccountActivity.class);
    }
    
    private void launchAbout(){
        startActivity(getAboutIntent());
    }
    
    private void launchSettingAccount(){
		startActivity(getSettingAccountIntent());
    }
    
	static class ItemClickListener implements AdapterView.OnItemClickListener {
		private Vector<LauncherListItem> items;
		
		public ItemClickListener(Vector<LauncherListItem> items){
			this.items = items;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			LauncherListItem item = items.get(position);
			item.onClick(view);
		}
	}
}
