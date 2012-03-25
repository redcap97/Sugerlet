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

package com.akr97.sugerlet;

import android.accounts.Account;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.akr97.sugerlet.contacts.*;
import com.akr97.sugerlet.list_item.*;

public class AccountListItem extends ListItem {
	private final Context context;
	private final Account account;

	public AccountListItem(Context context, Account account){
		super(Type.CUSTOM);
		
		this.context = context;
		this.account = account;
	}

	@Override
	public View getView(View convertView) {
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.account_list_item, null);

		TextView tvName = (TextView)convertView.findViewById(R.id.name);
		tvName.setText(account.name);
		TextView tvType = (TextView)convertView.findViewById(R.id.type);
		tvType.setText(account.type);

		return convertView;
	}

	@Override
	public void onClick(View view){
		context.startActivity(AccountContactsActivity.getIntent(context, account));
	}
}
