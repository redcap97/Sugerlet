package com.akr97.sugerlet.profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.listitem.*;

public class ProfileListEmailItem extends ListItem {
	private final EmailData email;

	public ProfileListEmailItem(Context context, EmailData email) {
		super(context, Type.CUSTOM);
		this.email = email;
	}

	@Override
	public View getView(View convertView) {
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.list_content_item, null);

		TextView name = (TextView)convertView.findViewById(R.id.textView);
		name.setText(email.address);
		return convertView;
	}

	@Override
	public void onClick(View view){
		Intent intent = new Intent(Intent.ACTION_SENDTO, email.getMailtoUri());
		context.startActivity(intent);
	}
}
