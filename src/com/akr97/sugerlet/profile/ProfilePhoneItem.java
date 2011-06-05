package com.akr97.sugerlet.profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.akr97.sugerlet.R;
import com.akr97.sugerlet.model.*;

public class ProfilePhoneItem extends ProfileListItem {
	private PhoneData phone;
	
	public ProfilePhoneItem(Context context, PhoneData phone) {
		super(context, Type.PHONE);
		this.phone = phone;
	}

	@Override
	public View getView(View convertView) {
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.profile_data_item, null);
	
		TextView name = (TextView)convertView.findViewById(R.id.textView);
		name.setText(phone.number);
		return convertView;
	}
	
	@Override
	public void onClick(View view){
		Intent intent = new Intent(Intent.ACTION_CALL, phone.getTelephoneUri());
		context.startActivity(intent);
	}
}
