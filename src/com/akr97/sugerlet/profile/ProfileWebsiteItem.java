package com.akr97.sugerlet.profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.akr97.sugerlet.R;
import com.akr97.sugerlet.model.*;

public class ProfileWebsiteItem extends ProfileListItem {
	private WebsiteData website;
	
	public ProfileWebsiteItem(Context context, WebsiteData website) {
		super(context, Type.WEBSITE);
		this.website = website;
	}

	@Override
	public View getView(View convertView) {
		LayoutInflater inflater = LayoutInflater.from(this.context);
		convertView = inflater.inflate(R.layout.profile_data_item, null);
	
		TextView name = (TextView)convertView.findViewById(R.id.textView);
		name.setText(website.url);
		return convertView;
	}
	
	@Override
	public void onClick(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, website.getUri());
        context.startActivity(intent);
	}
}
