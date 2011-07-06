package com.akr97.sugerlet.profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.list_item.*;

public class ProfileContentItem extends ListItem {
	private final Context context;
	private final String label;
	private final String content;
	private final Intent intent;

	public ProfileContentItem(Context context, String label, String content, Intent intent) {
		super(Type.CUSTOM);
		
		this.context = context;
		this.label = label;
		this.content = content;
		this.intent = intent;
	}
	
	public ProfileContentItem(Context context, String label, String content){
		super(Type.CUSTOM);
		
		this.context = context;
		this.label = label;
		this.content = content;
		this.intent = null;
	}

	@Override
	public View getView(View convertView) {
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.profile_content_item, null);
		
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
