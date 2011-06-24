package com.akr97.sugerlet;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.akr97.sugerlet.listitem.*;

public class InitialsGroupListActivity extends Activity {
	static final char[] INITIALS_GROUPS = new char[]{
		'あ', 'か', 'さ', 'た', 'な', 'は', 'ま', 'や', 'ら', 'わ'
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		setupInitialsIndex();
	}
	
	public void setupInitialsIndex(){
        ArrayList<ListItem> items = new ArrayList<ListItem>();
        
        for(char group : INITIALS_GROUPS){
            items.add(new ListIntentItem(this,
            		String.valueOf(group) + "行",
            		ContactListActivity.getIntentInitialsGroup(this, group)));
        }
        items.add(new ListIntentItem(this,
        		getString(R.string.alphabet),
        		ContactListActivity.getIntentInitialsGroup(this, 'A')));
        items.add(new ListIntentItem(this,
        		getString(R.string.number),
        		ContactListActivity.getIntentInitialsGroup(this, '0')));
        items.add(new ListIntentItem(this,
        		getString(R.string.other),
        		ContactListActivity.getIntentInitialsGroup(this, '~')));
        
        ListView listView = (ListView)findViewById(R.id.contactList);
        TextView textView = (TextView)findViewById(R.id.emptyView);
        textView.setText(getString(R.string.message_no_group));
        listView.setEmptyView(textView);
        listView.setAdapter(new ListItemAdapter(items));
        listView.setOnItemClickListener(new ListItemClickListener(items));
	}
	
	public static Intent getIntent(Context context){
    	return new Intent(context, InitialsGroupListActivity.class);
	}
}
