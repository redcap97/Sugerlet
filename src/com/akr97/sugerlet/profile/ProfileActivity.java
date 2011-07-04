package com.akr97.sugerlet.profile;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.list_item.*;

public class ProfileActivity extends Activity {
	static final String TAG = "com.akr97.sugerlet.model.ProfileActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		Parameter params = new Parameter();
		setTitle(getString(R.string.profile));
		setupHeader(params.rawContactId);
		setupProfileList(params.rawContactId);
	}

	public void setupHeader(long rawContactId){
		PhotoDao photoDao = new PhotoDao(this);
		PhotoData photo = photoDao.getByRawContactId(rawContactId);

		ImageView imageView = (ImageView)findViewById(R.id.imageView);
		imageView.setImageBitmap(photo.getBitmap());

		StructuredNameDao structuredNameDao = new StructuredNameDao(this);
		StructuredNameData structuredName = structuredNameDao.getByRawContactsId(rawContactId);

		TextView tvName = (TextView)findViewById(R.id.name);
		tvName.setText(structuredName.getName(getString(R.string.no_name_with_mark)));

		TextView tvPhoneticName = (TextView)findViewById(R.id.phoneticName);
		tvPhoneticName.setText(structuredName.getPhoneticName(getString(R.string.nothing_with_mark)));
	}

	public void setupProfileList(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		items.addAll(getPhoneList(rawContactId));
		items.addAll(getEmailList(rawContactId));
		items.addAll(getImList(rawContactId));
		items.addAll(getGroupList(rawContactId));
		items.addAll(getOtherList(rawContactId));

		ListView listView = (ListView)findViewById(R.id.listView);
		listView.setAdapter(new ListItemAdapter(items));
		listView.setOnItemClickListener(new ListItemClickListener(items));
	}

	ArrayList<ListItem> getPhoneList(long rawContactId){
		PhoneDao phoneDao = new PhoneDao(this);
		ArrayList<PhoneData> phones = phoneDao.get(rawContactId);

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
		EmailDao emailDao = new EmailDao(this);
		ArrayList<EmailData> emails = emailDao.get(rawContactId);

		ArrayList<ListItem> items = new ArrayList<ListItem>();
		if(!emails.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_email)));

			for(EmailData email : emails){
				String label = emailDao.getTypeLabel(email);
				Intent intent = new Intent(Intent.ACTION_SENDTO, email.getMailtoUri());

				items.add(new ProfileContentItem(this, label, email.address, intent));
			}
		}
		return items;
	}

	ArrayList<ListItem> getImList(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		ImDao imDao = new ImDao(this);
		ArrayList<ImData> ims = imDao.get(rawContactId);
		if(!ims.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_im)));
			for(ImData im : ims){
				String protocol = imDao.getProtocolLabel(im);
				items.add(new ProfileContentItem(this, protocol, im.data));
			}
		}
		return items;
	}

	ArrayList<ListItem> getGroupList(long rawContactId){
		GroupMembershipDao groupDao = new GroupMembershipDao(this);
		ArrayList<GroupData> groups = groupDao.get(rawContactId);

		ArrayList<ListItem> items = new ArrayList<ListItem>();

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

		StructuredPostalDao structuredPostalDao = new StructuredPostalDao(this);
		for(StructuredPostalData postal : structuredPostalDao.get(rawContactId)){
			String label = structuredPostalDao.getTypeLabel(postal);

			items.add(new ProfileContentItem(this, label, postal.formattedAddress));
		}
		return items;
	}

	ArrayList<ListItem> getNicknameList(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		NicknameDao nicknameDao = new NicknameDao(this);
		for(NicknameData nickname : nicknameDao.get(rawContactId)){
			String label = getString(R.string.nickname);

			items.add(new ProfileContentItem(this, label, nickname.data));
		}
		return items;
	}

	ArrayList<ListItem> getWebsiteList(long rawContactId){
		WebsiteDao websiteDao = new WebsiteDao(this);

		ArrayList<ListItem> items = new ArrayList<ListItem>();
		for(WebsiteData website : websiteDao.get(rawContactId)){
			String label = getString(R.string.website);
			Intent intent = new Intent(Intent.ACTION_VIEW, website.getUri());

			items.add(new ProfileContentItem(this, label, website.url, intent));
		}
		return items;
	}

	ArrayList<ListItem> getEventList(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		EventDao eventDao = new EventDao(this);
		for(EventData event : eventDao.get(rawContactId)){
			String label = eventDao.getTypeLabel(event);
			
			items.add(new ProfileContentItem(this, label, event.startDate));
		}
		return items;
	}

	ArrayList<ListItem> getOrganizationList(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		OrganizationDao organizationDao = new OrganizationDao(this);
		for(OrganizationData organization : organizationDao.get(rawContactId)){
			String label = organizationDao.getTypeLabel(organization);
			String content = organization.get();

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
