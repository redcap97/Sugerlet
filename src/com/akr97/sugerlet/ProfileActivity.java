package com.akr97.sugerlet;

import com.akr97.sugerlet.model.GroupModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		Parameter params = new Parameter();
		
		TextView tv = (TextView)findViewById(R.id.textView1);
		tv.setText(String.valueOf(params.rawContactId));
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
