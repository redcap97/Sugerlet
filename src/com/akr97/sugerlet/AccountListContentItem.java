package com.akr97.sugerlet;

import android.accounts.Account;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.akr97.sugerlet.contacts.*;
import com.akr97.sugerlet.list_item.*;

public class AccountListContentItem extends ListItem {
	private Account account;

	public AccountListContentItem(Context context, Account account){
		super(context, Type.CUSTOM);
		this.account = account;
	}

	@Override
	public View getView(View convertView) {
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.account_list_content_item, null);

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
