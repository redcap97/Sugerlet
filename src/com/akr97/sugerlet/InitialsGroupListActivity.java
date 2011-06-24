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
	static final char[] INITIALS = new char[]{
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
        
        for(char initial : INITIALS){
            items.add(new ListContentItem(this, initial + "行"));
        }
        items.add(new ListIntentItem(this,
        		getString(R.string.alphabet),
        		ContactListActivity.getIntentInitialsGroup(this)));
        items.add(new ListContentItem(this, getString(R.string.other)));
        
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
