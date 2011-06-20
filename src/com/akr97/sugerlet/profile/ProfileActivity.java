package com.akr97.sugerlet.profile;

import java.util.ArrayList;

import android.app.Activity;
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
		StructuredNameModel structuredNameModel = new StructuredNameModel(this);
		StructuredNameData structuredName = structuredNameModel.getByRawContactsId(rawContactId);
		
		TextView tvName = (TextView)findViewById(R.id.name);
		tvName.setText(structuredName.getName());
		
		TextView tvPhoneticName = (TextView)findViewById(R.id.phoneticName);
		tvPhoneticName.setText(structuredName.getPhoneticName(getString(R.string.nothing)));
	}
	
	public void setupProfileList(long rawContactId){
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		items.addAll(getPhoneList(rawContactId));
		items.addAll(getEmailList(rawContactId));
		items.addAll(getImList(rawContactId));
		items.addAll(getStructuredPostalList(rawContactId));
		items.addAll(getGroupList(rawContactId));
		items.addAll(getNicknameList(rawContactId));
		items.addAll(getWebsiteList(rawContactId));
		
		ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(new ListItemAdapter(items));
        listView.setOnItemClickListener(new ListItemClickListener(items));
	}
	
	ArrayList<ListItem> getPhoneList(long rawContactId){
        PhoneModel phoneModel = new PhoneModel(this);
        ArrayList<PhoneData> phones = phoneModel.get(rawContactId);
        
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
		EmailModel emailModel = new EmailModel(this);
		ArrayList<EmailData> emails = emailModel.get(rawContactId);
		
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
		ImModel imModel = new ImModel(this);
		ArrayList<ImData> ims = imModel.get(rawContactId);
		
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		if(!ims.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_im)));
			
			for(ImData im : ims){
				String content = String.format("(%s) %s", imModel.getProtocolLabel(im), im.data);
				items.add(new ListContentItem(this, content));
			}
		}
		return items;
	}
	
	ArrayList<ListItem> getStructuredPostalList(long rawContactId){
		StructuredPostalModel structuredPostalModel = new StructuredPostalModel(this);
		ArrayList<StructuredPostalData> postals = structuredPostalModel.get(rawContactId);
		
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
        GroupMembershipModel groupModel = new GroupMembershipModel(this);
        ArrayList<GroupData> groups = groupModel.get(rawContactId);
        
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		if(!groups.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_group)));
			
			for(GroupData group : groups){
				items.add(new ListContentItem(this, group.title));
			}
		}
        return items;
	}
	
	ArrayList<ListItem> getNicknameList(long rawContactId){
		NicknameModel nicknameModel = new NicknameModel(this);
		ArrayList<NicknameData> nicknames = nicknameModel.get(rawContactId);
		
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
		WebsiteModel websiteModel = new WebsiteModel(this);
		ArrayList<WebsiteData> websites = websiteModel.get(rawContactId);
		
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		if(!websites.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_website)));
			
			for(WebsiteData website : websites){
				items.add(new ProfileListWebsiteItem(this, website));
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
			long groupId = intent.getLongExtra(getString(R.string.key_raw_contact_id), -1);
			if(groupId == -1L){
				throw new RuntimeException("RawContactId is not found.");
			}
			return groupId;
		}
	}
}
