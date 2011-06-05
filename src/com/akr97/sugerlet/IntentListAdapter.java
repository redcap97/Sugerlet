package com.akr97.sugerlet;

import java.util.Vector;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class IntentListAdapter extends BaseAdapter {
	private Vector<IntentListItem> items;
	
	public IntentListAdapter(Vector<IntentListItem> items){
		this.items = items;
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
		IntentListItem item = (IntentListItem)items.get(position);
		return item.getView(convertView);
	}
}
