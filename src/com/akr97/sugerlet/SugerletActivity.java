package com.akr97.sugerlet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.database.Cursor;

import android.util.Log;
import android.net.*;
import android.provider.ContactsContract.*;

public class SugerletActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.add("red");
        adapter.add("green");
        adapter.add("blue");
        
        ListView listView = (ListView)findViewById(R.id.mainListView);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        			int position, long id){
        		ListView listView = (ListView)parent;
        		String item = (String)listView.getItemAtPosition(position);
        		Toast.makeText(SugerletActivity.this, item, Toast.LENGTH_LONG).show();
        	}
        });
        
        Cursor c = getContentResolver().query(Contacts.CONTENT_URI, new String[]{Contacts.DISPLAY_NAME}, null, null, null);
        try {
            c.moveToFirst();
            String displayName = c.getString(0);
            Toast.makeText(this, displayName, Toast.LENGTH_LONG).show();
        } finally {
            c.close();
        }
    }
}