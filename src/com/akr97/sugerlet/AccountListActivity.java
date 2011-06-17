package com.akr97.sugerlet;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
			items.add(new ListContentItem(this, state.getName()));
		}
        ListView listView = (ListView)findViewById(R.id.contactList);
        TextView textView = (TextView)findViewById(R.id.emptyView);
        textView.setText(getString(R.string.message_no_account));
        listView.setEmptyView(textView);
        listView.setAdapter(new ListItemAdapter(items));
        listView.setOnItemClickListener(new ItemClickListener(items));
	}
	
	static class ItemClickListener implements AdapterView.OnItemClickListener {
		private final ArrayList<ListItem> items;
		
		public ItemClickListener(ArrayList<ListItem> items){
			this.items = items;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ListItem item = items.get(position);
			item.onClick(view);
		}
	}
}
