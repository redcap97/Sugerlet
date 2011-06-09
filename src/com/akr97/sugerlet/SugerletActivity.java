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

import com.akr97.sugerlet.listitem.*;

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
        Vector<ListItem> items = new Vector<ListItem>();
        items.add(new ListHeaderItem(this, 
        		getString(R.string.header_menu)));
        items.add(new ListIntentItem(this,
        		getString(R.string.menu_group_list),
        		getGroupListIntent()));
        
        items.add(new ListHeaderItem(this,
        		getString(R.string.header_setting)));
        items.add(new ListIntentItem(this,
        		getString(R.string.menu_setting_account),
        		getSettingAccountIntent()));
        items.add(new ListIntentItem(this,
        		getString(R.string.menu_about),
        		getAboutIntent()));
        
        ListView listView = (ListView)findViewById(R.id.contactList);
        listView.setAdapter(new ListItemAdapter(items));
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
		private Vector<ListItem> items;
		
		public ItemClickListener(Vector<ListItem> items){
			this.items = items;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			ListItem item = items.get(position);
			item.onClick(view);
		}
	}
}
