package com.akr97.sugerlet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class IntentLauncherItem extends IntentListItem {
	private String title;
	private Intent intent;
	
	public IntentLauncherItem(Context context, String title, Intent intent) {
		super(context, Type.LAUNCHER);
		this.title = title;
		this.intent = intent;
	}

	@Override
	public View getView(View convertView){
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.profile_data_item, null);
	
		TextView name = (TextView)convertView.findViewById(R.id.textView);
		name.setText(title);
		
		return convertView;
	}
	
	@Override
	public void onClick(View view){
		context.startActivity(intent);
	}
}