package com.akr97.sugerlet;

import java.util.Vector;

import com.akr97.sugerlet.model.GroupData;
import com.akr97.sugerlet.model.GroupModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class GroupListActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setupGroupList();
	}
	
	private void setupGroupList(){
        Intent intent = getGroupListIntent(ContactListActivity.NO_GROUP_ID, getString(R.string.no_group));
        
        Vector<GroupListItem> items = new Vector<GroupListItem>();
        items.add(new GroupListHeaderItem(this, "GroupList"));
        items.add(new GroupListIntentItem(this, getString(R.string.no_group), intent));

        GroupModel model = new GroupModel(this);
        for(GroupData group : model.get()){
        	items.add(new GroupListIntentItem(this, group.title, intent));
        }
        
        ListView listView = (ListView)findViewById(R.id.contactList);
        listView.setEmptyView(findViewById(R.id.emptyView));
        listView.setAdapter(new GroupListAdapter(items));
        listView.setOnItemClickListener(new ItemClickListener(items));
	}
	
	private Intent getContactListIntent(){
		return new Intent(this, ContactListActivity.class);
	}
	
	private Intent getGroupListIntent(long groupId, String title){
		Intent intent = getContactListIntent();
		intent.putExtra(getString(R.string.key_of_group_id), groupId);
		intent.putExtra(getString(R.string.key_of_group_title), title);
		return intent;
	}
	
	static class ItemClickListener implements AdapterView.OnItemClickListener {
		private final Vector<GroupListItem> items;
		
		public ItemClickListener(Vector<GroupListItem> items){
			this.items = items;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			GroupListItem item = items.get(position);
			item.onClick(view);
		}
		
	}
}
