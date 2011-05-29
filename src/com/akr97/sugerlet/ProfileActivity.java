package com.akr97.sugerlet;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
//import android.util.Log;

import com.akr97.sugerlet.model.*;

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
		StructuredNameModel structuredNameModel = new StructuredNameModel(this);
		StructuredNameData structuredName = structuredNameModel.getByRawContactsId(rawContactId);
		
		TextView tvName = (TextView)findViewById(R.id.name);
		tvName.setText(structuredName.getName());
		
		TextView tvPhoneticName = (TextView)findViewById(R.id.phoneticName);
		tvPhoneticName.setText(structuredName.getPhoneticName(getString(R.string.nothing)));
	}
	
	public void setupProfileList(long rawContactId){
		Vector<ProfileListItem> items = new Vector<ProfileListItem>();
		items.addAll(getPhoneList(rawContactId));
		items.addAll(getEmailList(rawContactId));
		items.addAll(getImList(rawContactId));
		items.addAll(getStructuredPostalList(rawContactId));
		items.addAll(getGroupList(rawContactId));
		items.addAll(getNicknameList(rawContactId));
		items.addAll(getWebsiteList(rawContactId));
		
		ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(new ProfileListAdapter(items));
	}
	
	Vector<ProfileListItem> getPhoneList(long rawContactId){
        PhoneModel phoneModel = new PhoneModel(this);
        Vector<PhoneData> phones = phoneModel.get(rawContactId);
        
		Vector<ProfileListItem> items = new Vector<ProfileListItem>();
        if(!phones.isEmpty()){
    		items.add(new ProfileHeaderItem(this, getString(R.string.header_of_phone)));
    		
        	for(PhoneData phone : phones){
        		items.add(new ProfileContentItem(this, phone.number));
        	}
        }
        return items;
	}
	
	Vector<ProfileListItem> getEmailList(long rawContactId){
		EmailModel emailModel = new EmailModel(this);
		Vector<EmailData> emails = emailModel.get(rawContactId);
		
		Vector<ProfileListItem> items = new Vector<ProfileListItem>();
		if(!emails.isEmpty()){
			items.add(new ProfileHeaderItem(this, getString(R.string.header_of_email)));
			
			for(EmailData email : emails){
				items.add(new ProfileContentItem(this, email.address));
			}
		}
		return items;
	}
	
	Vector<ProfileListItem> getImList(long rawContactId){
		ImModel imModel = new ImModel(this);
		Vector<ImData> ims = imModel.get(rawContactId);
		
		Vector<ProfileListItem> items = new Vector<ProfileListItem>();
		if(!ims.isEmpty()){
			items.add(new ProfileHeaderItem(this, getString(R.string.header_of_im)));
			
			for(ImData im : ims){
				String content = String.format("(%s) %s", imModel.getProtocolLabel(im), im.data);
				items.add(new ProfileContentItem(this, content));
			}
		}
		return items;
	}
	
	Vector<ProfileListItem> getStructuredPostalList(long rawContactId){
		StructuredPostalModel structuredPostalModel = new StructuredPostalModel(this);
		Vector<StructuredPostalData> postals = structuredPostalModel.get(rawContactId);
		
		Vector<ProfileListItem> items = new Vector<ProfileListItem>();
		if(!postals.isEmpty()){
			items.add(new ProfileHeaderItem(this, getString(R.string.header_of_postal)));
			
			for(StructuredPostalData postal : postals){
				items.add(new ProfileContentItem(this, postal.formattedAddress));
			}
		}
		return items;
	}
	
	Vector<ProfileListItem> getGroupList(long rawContactId){
        GroupMembershipModel groupModel = new GroupMembershipModel(this);
        Vector<GroupData> groups = groupModel.get(rawContactId);
        
		Vector<ProfileListItem> items = new Vector<ProfileListItem>();
		if(!groups.isEmpty()){
			items.add(new ProfileHeaderItem(this, getString(R.string.header_of_group)));
			
			for(GroupData group : groups){
				items.add(new ProfileContentItem(this, group.title));
			}
		}
        return items;
	}
	
	Vector<ProfileListItem> getNicknameList(long rawContactId){
		NicknameModel nicknameModel = new NicknameModel(this);
		Vector<NicknameData> nicknames = nicknameModel.get(rawContactId);
		
		Vector<ProfileListItem> items = new Vector<ProfileListItem>();
		if(!nicknames.isEmpty()){
			items.add(new ProfileHeaderItem(this, getString(R.string.header_of_nickname)));
			
			for(NicknameData nickname : nicknames){
				items.add(new ProfileContentItem(this, nickname.name));
			}
		}
		return items;
	}
	
	Vector<ProfileListItem> getWebsiteList(long rawContactId){
		WebsiteModel websiteModel = new WebsiteModel(this);
		Vector<WebsiteData> websites = websiteModel.get(rawContactId);
		
		Vector<ProfileListItem> items = new Vector<ProfileListItem>();
		if(!websites.isEmpty()){
			items.add(new ProfileHeaderItem(this, getString(R.string.header_of_website)));
			
			for(WebsiteData website : websites){
				items.add(new ProfileContentItem(this, website.url));
			}
		}
		return items;
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
