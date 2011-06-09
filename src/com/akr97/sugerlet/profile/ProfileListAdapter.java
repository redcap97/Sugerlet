package com.akr97.sugerlet.profile;

import java.util.*;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.akr97.sugerlet.listitem.*;

public class ProfileListAdapter extends BaseAdapter {
	private Vector<ListItem> items;
	
	public ProfileListAdapter(Vector<ListItem> items){
		this.items = items;
	}
	
	@Override
	public boolean isEnabled(int position){
		ListItem item = (ListItem)getItem(position);
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
		ListItem item = (ListItem)getItem(position);
		return item.getView(convertView);
	}
}
