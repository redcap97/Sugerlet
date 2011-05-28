package com.akr97.sugerlet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ProfileDataItem extends ProfileListItem {
	private String content;
	
	public ProfileDataItem(Context context, String content) {
		super(context, Type.DATA);
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
