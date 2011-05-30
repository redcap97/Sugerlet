package com.akr97.sugerlet.profile;

import java.util.*;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
//import android.util.Log;

public class ProfileListAdapter extends BaseAdapter {
	private Vector<ProfileListItem> items;
	
	public ProfileListAdapter(Vector<ProfileListItem> items){
		this.items = items;
	}
	
	@Override
	public boolean isEnabled(int position){
		ProfileListItem item = (ProfileListItem)getItem(position);
		return !item.isHeader();
	}
	

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ProfileListItem item = (ProfileListItem)getItem(position);
		return item.getView(convertView);
	}
}
