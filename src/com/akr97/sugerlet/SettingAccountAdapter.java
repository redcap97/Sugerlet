package com.akr97.sugerlet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

public class SettingAccountAdapter extends BaseAdapter {
	private Context context;
	private AccountStateManager accountStateManager;
	
	public SettingAccountAdapter(Context context){
		this.context = context;
		this.accountStateManager = AccountStateManagerFactory.create(context);
	}
	
	@Override
	public int getCount() {
		return accountStateManager.getAccountSize();
	}

	@Override
	public Object getItem(int position) {
		return accountStateManager.getState(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AccountStateManager.State state = (AccountStateManager.State)getItem(position);
		ViewHolder holder;
		
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(this.context);
			convertView = inflater.inflate(R.layout.setting_account_item, null);
			
			holder = new ViewHolder();
			holder.name = (TextView)convertView.findViewById(R.id.name);
			holder.type = (TextView)convertView.findViewById(R.id.type);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.name.setText(state.getName());
		holder.type.setText(state.getType());
		
		return convertView;
	}
	
	static class ViewHolder{
		public TextView name;
		public TextView type;
	}
}
