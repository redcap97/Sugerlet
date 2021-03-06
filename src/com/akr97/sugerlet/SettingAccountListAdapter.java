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

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.akr97.sugerlet.account_state.*;

public class SettingAccountListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<AccountState> states = new ArrayList<AccountState>();

	public SettingAccountListAdapter(Context context){
		this.context = context;
		reset();
	}

	@Override
	public int getCount() {
		return states.size();
	}

	@Override
	public Object getItem(int position) {
		return states.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AccountState state = (AccountState)getItem(position);
		ViewHolder holder;

		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(this.context);
			convertView = inflater.inflate(R.layout.setting_account_list_item, null);

			holder = new ViewHolder();
			holder.name = (TextView)convertView.findViewById(R.id.name);
			holder.type = (TextView)convertView.findViewById(R.id.type);
			holder.checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}

		holder.name.setText(state.getName());
		holder.type.setText(state.getType());
		holder.checkBox.setOnCheckedChangeListener(new CheckedChangeListener(position));
		holder.checkBox.setChecked(state.isEnabled());

		return convertView;
	}

	public void save(){
		AccountStateManager accountStateManager = AccountStateManagerFactory.create(context);
		for(AccountState state : states){
			accountStateManager.update(state);
		}
		accountStateManager.save();
	}

	public void reset(){
		states.clear();
		AccountStateManager manager = AccountStateManagerFactory.create(context);
		for(AccountState state : manager.getStates()){
			states.add(new AccountState(state));
		}
	}

	static class ViewHolder{
		public TextView name;
		public TextView type;
		public CheckBox checkBox;
	}

	class CheckedChangeListener implements OnCheckedChangeListener{
		private int position;

		public CheckedChangeListener(int position){
			this.position = position;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			AccountState state = states.get(position);
			state.setEnabled(isChecked);
		}
	}
}
