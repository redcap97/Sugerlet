package com.akr97.sugerlet.profile;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.listitem.*;

public class ProfileActivity extends Activity {
	static final String TAG = "com.akr97.sugerlet.model.ProfileActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		Parameter params = new Parameter();
		setupHeader(params.rawContactId);
		setupProfileList(params.rawContactId);
	}
	
	public void setupHeader(long rawContactId){
		StructuredNameDao structuredNameDao = new StructuredNameDao(this);
		StructuredNameData structuredName = structuredNameDao.getByRawContactsId(rawContactId);
		
		TextView tvName = (TextView)findViewById(R.id.name);
		tvName.setText(structuredName.getName());
		
		TextView tvPhoneticName = (TextView)findViewById(R.id.phoneticName);
		tvPhoneticName.setText(structuredName.getPhoneticName(getString(R.string.nothing)));
	}
	
	public void setupProfileList(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		items.addAll(getPhoneList(rawContactId));
		items.addAll(getEmailList(rawContactId));
		items.addAll(getNicknameList(rawContactId));
		items.addAll(getStructuredPostalList(rawContactId));
		items.addAll(getImList(rawContactId));
		items.addAll(getGroupList(rawContactId));
		items.addAll(getWebsiteList(rawContactId));
		items.addAll(getEventList(rawContactId));
		items.addAll(getOrganizationList(rawContactId));

		ListView listView = (ListView)findViewById(R.id.list);
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
        		items.add(new ProfileListPhoneItem(this, phone));
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
				items.add(new ProfileListEmailItem(this, email));
			}
		}
		return items;
	}
	
	ArrayList<ListItem> getImList(long rawContactId){
		ImDao imDao = new ImDao(this);
		ArrayList<ImData> ims = imDao.get(rawContactId);
		
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		if(!ims.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_im)));
			
			for(ImData im : ims){
				String content = String.format("(%s) %s", imDao.getProtocolLabel(im), im.data);
				items.add(new ListContentItem(this, content));
			}
		}
		return items;
	}
	
	ArrayList<ListItem> getStructuredPostalList(long rawContactId){
		StructuredPostalDao structuredPostalDao = new StructuredPostalDao(this);
		ArrayList<StructuredPostalData> postals = structuredPostalDao.get(rawContactId);
		
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		if(!postals.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_postal)));
			
			for(StructuredPostalData postal : postals){
				items.add(new ListContentItem(this, postal.formattedAddress));
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
	
	ArrayList<ListItem> getNicknameList(long rawContactId){
		NicknameDao nicknameDao = new NicknameDao(this);
		ArrayList<NicknameData> nicknames = nicknameDao.get(rawContactId);
		
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		if(!nicknames.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_nickname)));
			
			for(NicknameData nickname : nicknames){
				items.add(new ListContentItem(this, nickname.name));
			}
		}
		return items;
	}
	
	ArrayList<ListItem> getWebsiteList(long rawContactId){
		WebsiteDao websiteDao = new WebsiteDao(this);
		ArrayList<WebsiteData> websites = websiteDao.get(rawContactId);
		
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		if(!websites.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_website)));
			
			for(WebsiteData website : websites){
				items.add(new ProfileListWebsiteItem(this, website));
			}
		}
		return items;
	}
	
	ArrayList<ListItem> getEventList(long rawContactId){
		EventDao eventDao = new EventDao(this);
		ArrayList<EventData> events = eventDao.get(rawContactId);
		
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		if(!events.isEmpty()){
			items.add(new ListHeaderItem(this, "Event"));
			
			for(EventData event : events){
				items.add(new ListContentItem(this, event.toString()));
			}
		}
		return items;
	}
	
	ArrayList<ListItem> getOrganizationList(long rawContactId){
		OrganizationDao organizationDao = new OrganizationDao(this);
		ArrayList<OrganizationData> organizations = organizationDao.get(rawContactId);
		
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		if(!organizations.isEmpty()){
			items.add(new ListHeaderItem(this, "Organization"));
			
			for(OrganizationData organization : organizations){
				items.add(new ListContentItem(this, organization.toString()));
			}
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
