package com.akr97.sugerlet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.akr97.sugerlet.model.*;

public class ProfileActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		Parameter params = new Parameter();
		StructuredNameModel model = new StructuredNameModel(this);
		StructuredNameData structuredName = model.getByRawContactsId(params.rawContactId);
		
		TextView tvName = (TextView)findViewById(R.id.name);
		tvName.setText(structuredName.getName());
		
		TextView tvPhoneticName = (TextView)findViewById(R.id.phoneticName);
		tvPhoneticName.setText(structuredName.getPhoneticName());
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		
		ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        
        PhoneModel phoneModel = new PhoneModel(this);
        for(PhoneData r : phoneModel.get(params.rawContactId)){
        	adapter.add(r.number);
        }
	}
	
	class Parameter{
		private Intent intent;
		
		public long rawContactId;
		
		public Parameter(){
			this.intent = getIntent();
			this.rawContactId = getRawContactId();
		}
		
		private long getRawContactId(){
			long groupId = intent.getLongExtra(getString(R.string.key_of_raw_contact_id), -1);
			if(groupId == -1L){
				throw new RuntimeException("RawContactId is not found.");
			}
			return groupId;
		}
	}
}
