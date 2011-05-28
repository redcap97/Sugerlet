package com.akr97.sugerlet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
//import android.util.Log;

public class ProfileHeaderItem extends ProfileListItem {
	private String title;
	
	public ProfileHeaderItem(Context context, String title) {
		super(context, Type.HEADER);
		this.title = title;
	}
	
	@Override
	public View getView(View convertView){
		LayoutInflater inflater = LayoutInflater.from(this.context);
		convertView = inflater.inflate(R.layout.profile_header_item, null);
		
		TextView name = (TextView)convertView.findViewById(R.id.title);
		name.setText(this.title);
		
		return convertView;
	}
}
