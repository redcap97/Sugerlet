package com.akr97.sugerlet;

import java.util.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.akr97.sugerlet.model.*;

public class SugerletActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        Vector<ContactSummaryModel> contacts = ContactSummaryModel.getAll(this);
        for(ContactSummaryModel contact : contacts){
        	adapter.add(contact.displayName);
        	if(contact.hasPhoneNumber){
        		Vector<PhoneModel> phones = PhoneModel.get(this, contact.id);
        		for(PhoneModel phone : phones){
        			adapter.add(phone.number);
        		}
        	}
        }
        
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
    }
}