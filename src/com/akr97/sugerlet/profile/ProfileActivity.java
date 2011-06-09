package com.akr97.sugerlet.profile;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
		Vector<ListItem> items = new Vector<ListItem>();
		items.addAll(getPhoneList(rawContactId));
		items.addAll(getEmailList(rawContactId));
		items.addAll(getImList(rawContactId));
		items.addAll(getStructuredPostalList(rawContactId));
		items.addAll(getGroupList(rawContactId));
		items.addAll(getNicknameList(rawContactId));
		items.addAll(getWebsiteList(rawContactId));
		
		ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(new ProfileListAdapter(items));
        listView.setOnItemClickListener(new ItemClickListener(items));
	}
	
	Vector<ListItem> getPhoneList(long rawContactId){
        PhoneModel phoneModel = new PhoneModel(this);
        Vector<PhoneData> phones = phoneModel.get(rawContactId);
        
		Vector<ListItem> items = new Vector<ListItem>();
        if(!phones.isEmpty()){
    		items.add(new ListHeaderItem(this, getString(R.string.header_phone)));
    		
        	for(PhoneData phone : phones){
        		items.add(new ProfileListPhoneItem(this, phone));
        	}
        }
        return items;
	}
	
	Vector<ListItem> getEmailList(long rawContactId){
		EmailModel emailModel = new EmailModel(this);
		Vector<EmailData> emails = emailModel.get(rawContactId);
		
		Vector<ListItem> items = new Vector<ListItem>();
		if(!emails.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_email)));
			
			for(EmailData email : emails){
				items.add(new ProfileListEmailItem(this, email));
			}
		}
		return items;
	}
	
	Vector<ListItem> getImList(long rawContactId){
		ImModel imModel = new ImModel(this);
		Vector<ImData> ims = imModel.get(rawContactId);
		
		Vector<ListItem> items = new Vector<ListItem>();
		if(!ims.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_im)));
			
			for(ImData im : ims){
				String content = String.format("(%s) %s", imModel.getProtocolLabel(im), im.data);
				items.add(new ListContentItem(this, content));
			}
		}
		return items;
	}
	
	Vector<ListItem> getStructuredPostalList(long rawContactId){
		StructuredPostalModel structuredPostalModel = new StructuredPostalModel(this);
		Vector<StructuredPostalData> postals = structuredPostalModel.get(rawContactId);
		
		Vector<ListItem> items = new Vector<ListItem>();
		if(!postals.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_postal)));
			
			for(StructuredPostalData postal : postals){
				items.add(new ListContentItem(this, postal.formattedAddress));
			}
		}
		return items;
	}
	
	Vector<ListItem> getGroupList(long rawContactId){
        GroupMembershipModel groupModel = new GroupMembershipModel(this);
        Vector<GroupData> groups = groupModel.get(rawContactId);
        
		Vector<ListItem> items = new Vector<ListItem>();
		if(!groups.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_group)));
			
			for(GroupData group : groups){
				items.add(new ListContentItem(this, group.title));
			}
		}
        return items;
	}
	
	Vector<ListItem> getNicknameList(long rawContactId){
		NicknameModel nicknameModel = new NicknameModel(this);
		Vector<NicknameData> nicknames = nicknameModel.get(rawContactId);
		
		Vector<ListItem> items = new Vector<ListItem>();
		if(!nicknames.isEmpty()){
			items.add(new ListHeaderItem(this, getString(R.string.header_nickname)));
			
			for(NicknameData nickname : nicknames){
				items.add(new ListContentItem(this, nickname.name));
			}
		}
		return items;
	}
	
	Vector<ListItem> getWebsiteList(long rawContactId){
		WebsiteModel websiteModel = new WebsiteModel(this);
		Vector<WebsiteData> websites = websiteModel.get(rawContactId);
		
		Vector<ListItem> items = new Vector<ListItem>();
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
	
	static class ItemClickListener implements AdapterView.OnItemClickListener {
		private Vector<ListItem> items;
		
		public ItemClickListener(Vector<ListItem> items){
			this.items = items;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			
			ListItem item = items.get(position);
			item.onClick(view);
		}
	}
}
