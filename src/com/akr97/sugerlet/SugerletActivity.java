package com.akr97.sugerlet;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
        
		Intent intent = new Intent(this, GroupListActivity.class);
		startActivity(intent);
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
    
    private void launchAbout(){
        Intent intent = new Intent(Intent.ACTION_VIEW, SUGERLET_URI);
        startActivity(intent);
    }
    
    private void launchSettingAccount(){
		Intent intent = new Intent(this, SettingAccountActivity.class);
		startActivity(intent);
    }
}
