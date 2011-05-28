package com.akr97.sugerlet;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.akr97.sugerlet.model.*;

public class ProfileActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		Parameter params = new Parameter();
		StructuredNameModel structuredNameModel = new StructuredNameModel(this);
		StructuredNameData structuredName = structuredNameModel.getByRawContactsId(params.rawContactId);
		
		TextView tvName = (TextView)findViewById(R.id.name);
		tvName.setText(structuredName.getName());
		
		TextView tvPhoneticName = (TextView)findViewById(R.id.phoneticName);
		tvPhoneticName.setText(structuredName.getPhoneticName());
		
		Vector<ProfileListItem> items = new Vector<ProfileListItem>();
		
        PhoneModel phoneModel = new PhoneModel(this);
        Vector<PhoneData> phones = phoneModel.get(params.rawContactId);
        
        if(!phones.isEmpty()){
    		items.add(new ProfileHeaderItem(this, getString(R.string.header_of_phone)));
    		
        	for(PhoneData phone : phones){
        		items.add(new ProfileDataItem(this, phone.number));
        	}
        }
		
		ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(new ProfileListAdapter(this, items));
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
