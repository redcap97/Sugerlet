package com.akr97.sugerlet;

import java.util.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;

import com.akr97.sugerlet.model.*;

public class SugerletActivity extends Activity {
	final static String TAG = "com.akr97.com.sugerlet.SugerActivity";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
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