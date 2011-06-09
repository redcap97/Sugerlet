package com.akr97.sugerlet.listitem;

import com.akr97.sugerlet.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ListContentItem extends ListItem {
	private final String content;
	
	public ListContentItem(Context context, String content){
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
