package com.akr97.sugerlet;

import java.util.*;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.QuickContactBadge;
import android.content.Context;

import android.provider.ContactsContract.*;

import com.akr97.sugerlet.model.*;

public class ContactListAdapter extends BaseAdapter {
	private Vector<ContactSummaryModel> contacts;
	private Context context;

	public ContactListAdapter(Context context) {
		this.contacts = ContactSummaryModel.getAll(context);
		this.context = context;
	}

	@Override
	public int getCount() {
		return contacts.size();
	}

	@Override
	public Object getItem(int position) {
		return contacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContactSummaryModel contact = (ContactSummaryModel)getItem(position);
		
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(this.context);
			convertView = inflater.inflate(R.layout.item_contact_summary, null);

			TextView tvName = (TextView)convertView.findViewById(R.id.textView1);
			tvName.setText(contact.displayName);
			
			TextView tvPhoneNumber = (TextView)convertView.findViewById(R.id.textView2);
			Vector<PhoneModel> phones = PhoneModel.get(this.context, contact.id);
			tvPhoneNumber.setText(phones.get(0).number);
			
			QuickContactBadge badge = (QuickContactBadge)convertView.findViewById(R.id.quickContactBadge1);
			badge.assignContactUri(Contacts.getLookupUri(contact.id, contact.lookupKey));
			badge.setMode(QuickContact.MODE_SMALL); 
		}
		
		return convertView;
	}
}
