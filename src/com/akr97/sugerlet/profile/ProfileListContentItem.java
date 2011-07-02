package com.akr97.sugerlet.profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.listitem.*;

public class ProfileListContentItem extends ListItem {
	private final String label;
	private final String content;
	private final Intent intent;

	public ProfileListContentItem(Context context, String label, String content, Intent intent) {
		super(context, Type.CUSTOM);
		this.label = label;
		this.content = content;
		this.intent = intent;
	}
	
	public ProfileListContentItem(Context context, String label, String content){
		super(context, Type.CUSTOM);
		this.label = label;
		this.content = content;
		this.intent = null;
	}

	@Override
	public View getView(View convertView) {
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.profile_list_content_item, null);
		
		TextView label = (TextView)convertView.findViewById(R.id.label);
		label.setText(this.label);

		TextView content = (TextView)convertView.findViewById(R.id.content);
		content.setText(this.content);
		return convertView;
	}

	@Override
	public void onClick(View view){
		if(intent != null){
			context.startActivity(intent);
		}
	}
}
