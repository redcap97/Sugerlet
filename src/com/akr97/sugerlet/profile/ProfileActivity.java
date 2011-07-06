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
		this.items = createListItems(params.rawContactId);
		this.listAdapter = new ListItemAdapter(items);

		setTitle(getString(R.string.profile));
		setupHeader();
		setupProfileList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add(Menu.NONE, MENU_EDIT_CONTACT, Menu.NONE, "Edit contact");
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

	public void setupHeader(){
		PhotoDao photoDao = new PhotoDao(this);
		PhotoData photo = photoDao.getByRawContactId(params.rawContactId);

		ImageView imageView = (ImageView)findViewById(R.id.imageView);
		imageView.setImageBitmap(photo.getBitmap());

		StructuredNameDao structuredNameDao = new StructuredNameDao(this);
		StructuredNameData structuredName = structuredNameDao.getByRawContactsId(params.rawContactId);

		TextView tvName = (TextView)findViewById(R.id.name);
		tvName.setText(structuredName.getName(getString(R.string.no_name_with_mark)));
		TextView tvPhoneticName = (TextView)findViewById(R.id.phoneticName);
		tvPhoneticName.setText(structuredName.getPhoneticName(getString(R.string.nothing_with_mark)));
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
		ArrayList<ListItem> changedItems = createListItems(params.rawContactId);
		items.clear();
		items.addAll(changedItems);
		listAdapter.notifyDataSetChanged();
	}

	ArrayList<ListItem> createListItems(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		items.addAll(getPhoneList(rawContactId));
		items.addAll(getEmailList(rawContactId));
		items.addAll(getImList(rawContactId));
		items.addAll(getGroupList(rawContactId));
		items.addAll(getOtherList(rawContactId));
		return items;
	}

	ArrayList<ListItem> getPhoneList(long rawContactId){
		PhoneDao dao = new PhoneDao(this);
		ArrayList<PhoneData> phones = dao.get(rawContactId);

		ArrayList<ListItem> items = new ArrayList<ListItem>();
		if(!phones.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_phone)));

			for(PhoneData phone : phones){
				items.add(new ProfilePhoneItem(this, phone));
			}
		}
		return items;
	}

	ArrayList<ListItem> getEmailList(long rawContactId){
		EmailDao dao = new EmailDao(this);
		ArrayList<EmailData> emails = dao.get(rawContactId);

		ArrayList<ListItem> items = new ArrayList<ListItem>();
		if(!emails.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_email)));

			for(EmailData email : emails){
				String label = dao.getTypeLabel(email);
				Intent intent = new Intent(Intent.ACTION_SENDTO, email.getMailtoUri());

				items.add(new ProfileContentItem(this, label, email.address, intent));
			}
		}
		return items;
	}

	ArrayList<ListItem> getImList(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		ImDao dao = new ImDao(this);
		ArrayList<ImData> ims = dao.get(rawContactId);
		if(!ims.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_im)));
			for(ImData im : ims){
				String protocol = dao.getProtocolLabel(im);
				items.add(new ProfileContentItem(this, protocol, im.data));
			}
		}
		return items;
	}

	ArrayList<ListItem> getGroupList(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		GroupMembershipDao dao = new GroupMembershipDao(this);
		ArrayList<GroupData> groups = dao.get(rawContactId);

		items.add(new ListHeaderItem(this, getString(R.string.header_group)));
		if(groups.isEmpty()){
			items.add(new ListContentItem(this, getString(R.string.no_group)));
		}else{
			for(GroupData group : groups){
				items.add(new ListContentItem(this, group.title));
			}
		}
		return items;
	}

	ArrayList<ListItem> getOtherList(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		items.addAll(getEventList(rawContactId));
		items.addAll(getWebsiteList(rawContactId));
		items.addAll(getStructuredPostalList(rawContactId));
		items.addAll(getNicknameList(rawContactId));
		items.addAll(getOrganizationList(rawContactId));
		items.addAll(getNoteList(rawContactId));

		if(!items.isEmpty()){
			ArrayList<ListItem> results = new ArrayList<ListItem>();
			results.add(new ListHeaderItem(this, getString(R.string.header_other)));
			results.addAll(items);
			return results;
		}
		return items;
	}

	ArrayList<ListItem> getStructuredPostalList(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		StructuredPostalDao dao = new StructuredPostalDao(this);
		for(StructuredPostalData postal : dao.get(rawContactId)){
			String label = dao.getTypeLabel(postal);

			items.add(new ProfileContentItem(this, label, postal.formattedAddress));
		}
		return items;
	}

	ArrayList<ListItem> getNicknameList(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		NicknameDao dao = new NicknameDao(this);
		for(NicknameData nickname : dao.get(rawContactId)){
			String label = getString(R.string.nickname);

			items.add(new ProfileContentItem(this, label, nickname.data));
		}
		return items;
	}

	ArrayList<ListItem> getWebsiteList(long rawContactId){
		WebsiteDao dao = new WebsiteDao(this);

		ArrayList<ListItem> items = new ArrayList<ListItem>();
		for(WebsiteData website : dao.get(rawContactId)){
			String label = getString(R.string.website);
			Intent intent = new Intent(Intent.ACTION_VIEW, website.getUri());

			items.add(new ProfileContentItem(this, label, website.url, intent));
		}
		return items;
	}

	ArrayList<ListItem> getEventList(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		EventDao dao = new EventDao(this);
		for(EventData event : dao.get(rawContactId)){
			String label = dao.getTypeLabel(event);

			items.add(new ProfileContentItem(this, label, event.startDate));
		}
		return items;
	}

	ArrayList<ListItem> getOrganizationList(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		OrganizationDao dao = new OrganizationDao(this);
		for(OrganizationData organization : dao.get(rawContactId)){
			String label = dao.getTypeLabel(organization);
			String content = organization.getDisplayName();

			items.add(new ProfileContentItem(this, label, content));
		}
		return items;
	}

	ArrayList<ListItem> getNoteList(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		NoteDao noteDao = new NoteDao(this);
		for(NoteData note : noteDao.get(rawContactId)){
			String label = getString(R.string.note);

			items.add(new ProfileContentItem(this, label, note.data));
		}
		return items;
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
