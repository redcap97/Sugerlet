package com.akr97.sugerlet.profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.listitem.*;

public class ProfilePhoneItem extends ListItem {
	private final PhoneData phone;

	public ProfilePhoneItem(Context context, PhoneData phone) {
		super(context, Type.CUSTOM);
		this.phone = phone;
	}

	@Override
	public View getView(View convertView) {
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.profile_phone_item, null);
		
		PhoneDao dao = new PhoneDao(context);
		TextView label = (TextView)convertView.findViewById(R.id.label);
		label.setText(dao.getTypeLabel(phone));

		TextView number = (TextView)convertView.findViewById(R.id.number);
		number.setText(phone.number);
		
		Button sms = (Button)convertView.findViewById(R.id.smsButton);
		sms.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, phone.getSmsUri());
				context.startActivity(intent);
			}
		});
		
		return convertView;
	}

	@Override
	public void onClick(View view){
		Intent intent = new Intent(Intent.ACTION_CALL, phone.getTelephoneUri());
		context.startActivity(intent);
	}
}
