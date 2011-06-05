package com.akr97.sugerlet.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.akr97.sugerlet.*;

public class ProfileContentItem extends ProfileListItem {
	private String content;
	
	public ProfileContentItem(Context context, String content) {
		super(context, Type.CONTENT);
		this.content = content;
	}
	
	@Override
	public View getView(View convertView){
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.profile_data_item, null);
	
		TextView name = (TextView)convertView.findViewById(R.id.textView);
		name.setText(content);
		
		return convertView;
	}
	
	@Override
	public void onClick(View view){
		android.widget.Toast.makeText(context, content, 20).show();
	}
}
