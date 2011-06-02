package com.akr97.sugerlet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class SettingAccountActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_account);
        
		ListView listView = (ListView)findViewById(R.id.list);
		listView.setAdapter(new SettingAccountAdapter(this));
    }
}
