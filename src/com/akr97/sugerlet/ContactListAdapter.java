package com.akr97.sugerlet;

import java.util.*;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.content.Context;
import android.util.Log;

import com.akr97.sugerlet.model.*;

public class ContactListAdapter extends BaseAdapter {
	private Vector<StructuredNameModel> structuredNames;
	private Context context;
	
	static final String TAG = "com.akr97.sugerlet.ContactListAdapter";

	public ContactListAdapter(Context context, Vector<StructuredNameModel> sns) {
		this.structuredNames = sns;
		this.context = context;
	}

	@Override
	public int getCount() {
		return structuredNames.size();
	}

	@Override
	public Object getItem(int position) {
		return structuredNames.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		StructuredNameModel structuredName = (StructuredNameModel)getItem(position);
		
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(this.context);
			convertView = inflater.inflate(R.layout.item_contact_summary, null);
		}
		
		TextView tvName = (TextView)convertView.findViewById(R.id.name);
		tvName.setText(structuredName.getName());
		
		TextView tvPhoneticName = (TextView)convertView.findViewById(R.id.phoneticName);
		tvPhoneticName.setText(structuredName.getPhoneticName());
		
		return convertView;
	}
}