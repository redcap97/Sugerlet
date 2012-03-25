/*
Copyright (C) 2012 Akira Midorikawa

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.akr97.sugerlet.profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.list_item.*;

public class ProfilePhoneItem extends ListItem {
	private final Context context;
	private final PhoneData phone;

	public ProfilePhoneItem(Context context, PhoneData phone) {
		super(Type.CUSTOM);
		
		this.context = context;
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
