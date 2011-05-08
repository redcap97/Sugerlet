package com.akr97.sugerlet;

import java.util.*;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;

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
		LinearLayout layout = new LinearLayout(this.context);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		TextView tvName = new TextView(this.context);
		tvName.setText(contact.displayName);
		layout.addView(tvName);
		
		TextView tvId = new TextView(this.context);
		tvId.setText(String.valueOf(contact.id));
		layout.addView(tvId);
		
		return layout;
	}
}
