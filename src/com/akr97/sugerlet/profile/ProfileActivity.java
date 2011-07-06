package com.akr97.sugerlet.profile;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.list_item.*;

public class ProfileActivity extends Activity {
	private Parameter params;
	private ArrayList<ListItem> items;
	private ListItemAdapter listAdapter;

	static final int MENU_EDIT_CONTACT = (Menu.FIRST + 1);
	static final String TAG = "com.akr97.sugerlet.model.ProfileActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		this.params = new Parameter();
		this.items = createListItems();
		this.listAdapter = new ListItemAdapter(items);

		setTitle(getString(R.string.profile));
		setupHeader();
		setupProfileList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add(Menu.NONE, MENU_EDIT_CONTACT, Menu.NONE, getString(R.string.menu_edit_contact));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case MENU_EDIT_CONTACT:
			editContact();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		resetHeader();
		resetProfileList();
	}

	public ArrayList<ListItem> createListItems(){
		ProfileListItemsFactory factory =
			new ProfileListItemsFactory(this, params.rawContactId);
		return factory.create();
	}

	public void setupHeader(){
		PhotoDao photoDao = new PhotoDao(this);
		PhotoData photo = photoDao.getByRawContactId(params.rawContactId);

		ImageView imageView = (ImageView)findViewById(R.id.imageView);
		imageView.setImageBitmap(photo.getBitmap());

		StructuredNameDao structuredNameDao = new StructuredNameDao(this);
		StructuredNameData structuredName = structuredNameDao.getByRawContactsId(params.rawContactId);

		TextView tvName = (TextView)findViewById(R.id.name);
		String name = structuredName.getName(getString(R.string.no_name_with_mark));
		tvName.setText(name);

		TextView tvPhoneticName = (TextView)findViewById(R.id.phoneticName);
		String phoneticName = structuredName.getPhoneticName(getString(R.string.nothing_with_mark));
		tvPhoneticName.setText(phoneticName);
	}

	public void resetHeader(){
		setupHeader();
	}

	public void setupProfileList(){
		ListView listView = (ListView)findViewById(R.id.listView);
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new ListItemClickListener(items));
	}

	public void resetProfileList(){
		ArrayList<ListItem> changedItems = createListItems();
		items.clear();
		items.addAll(changedItems);
		listAdapter.notifyDataSetChanged();
	}

	private void editContact(){
		Uri uri = Uri.withAppendedPath(ContactsContract.RawContacts.CONTENT_URI,
				String.valueOf(params.rawContactId));
		Intent intent = new Intent(Intent.ACTION_EDIT, uri);

		startActivityForResult(intent, 0);
	}

	public static Intent getIntent(Context context, long rawContactId){
		Intent intent = new Intent(context, ProfileActivity.class);
		intent.putExtra(context.getString(R.string.key_raw_contact_id), rawContactId);
		return intent;
	}

	class Parameter{
		private Intent intent;

		public long rawContactId;

		public Parameter(){
			this.intent = getIntent();
			this.rawContactId = getRawContactId();
		}

		private long getRawContactId(){
			long groupId = intent.getLongExtra(getString(R.string.key_raw_contact_id), -1);
			if(groupId == -1L){
				throw new RuntimeException("RawContactId is not found.");
			}
			return groupId;
		}
	}
}
