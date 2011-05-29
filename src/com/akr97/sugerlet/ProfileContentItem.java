package com.akr97.sugerlet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ProfileContentItem extends ProfileListItem {
	private String content;
	
	public ProfileContentItem(Context context, String content) {
		super(context, Type.CONTENT);
		this.content = content;
	}
	
	@Override
	public View getView(View convertView){
		LayoutInflater inflater = LayoutInflater.from(this.context);
		convertView = inflater.inflate(R.layout.profile_data_item, null);
	
		TextView name = (TextView)convertView.findViewById(R.id.textView);
		name.setText(this.content);
		
		return convertView;
	}
}