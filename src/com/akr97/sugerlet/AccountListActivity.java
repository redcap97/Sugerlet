package com.akr97.sugerlet;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.akr97.sugerlet.listitem.*;

public class AccountListActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setupAccountList();
	}
	
	public void setupAccountList(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		AccountStateManager manager = AccountStateManagerFactory.create(this);
		
		for(AccountState state : manager.getEnabledStates()){
			items.add(new AccountListContentItem(this, state.getAccount()));
		}
        ListView listView = (ListView)findViewById(R.id.contactList);
        TextView textView = (TextView)findViewById(R.id.emptyView);
        textView.setText(getString(R.string.message_no_account));
        listView.setEmptyView(textView);
        listView.setAdapter(new ListItemAdapter(items));
        listView.setOnItemClickListener(new ListItemClickListener(items));
	}
}
