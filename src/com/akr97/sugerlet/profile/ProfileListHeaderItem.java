package com.akr97.sugerlet.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.akr97.sugerlet.*;

public class ProfileListHeaderItem extends ProfileListItem {
	private String title;
	
	public ProfileListHeaderItem(Context context, String title) {
		super(context, Type.HEADER);
		this.title = title;
	}
	
	@Override
	public View getView(View convertView){
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.profile_header_item, null);
		
		TextView name = (TextView)convertView.findViewById(R.id.title);
		name.setText(title);
		
		return convertView;
	}
}
