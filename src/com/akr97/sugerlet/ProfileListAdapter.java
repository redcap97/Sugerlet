package com.akr97.sugerlet;

import java.util.*;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.util.Log;

import com.akr97.sugerlet.model.*;

public class ProfileListAdapter extends BaseAdapter {
	private Context context;
	private Vector<ProfileListItem> items;
	
	public ProfileListAdapter(Context context, Vector<ProfileListItem> items){
		this.context = context;
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
		ProfileListItem item = (ProfileListItem)getItem(position);
		return item.getView(convertView);
	}
}
