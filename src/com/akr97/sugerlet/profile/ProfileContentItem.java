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
import android.widget.TextView;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.list_item.*;

public class ProfileContentItem extends ListItem {
	private final Context context;
	private final String label;
	private final String content;
	private final Intent intent;

	public ProfileContentItem(Context context, String label, String content, Intent intent) {
		super(Type.CUSTOM);
		
		this.context = context;
		this.label = label;
		this.content = content;
		this.intent = intent;
	}
	
	public ProfileContentItem(Context context, String label, String content){
		super(Type.CUSTOM);
		
		this.context = context;
		this.label = label;
		this.content = content;
		this.intent = null;
	}

	@Override
	public View getView(View convertView) {
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.profile_content_item, null);
		
		TextView label = (TextView)convertView.findViewById(R.id.label);
		label.setText(this.label);

		TextView content = (TextView)convertView.findViewById(R.id.content);
		content.setText(this.content);
		return convertView;
	}

	@Override
	public void onClick(View view){
		if(intent != null){
			context.startActivity(intent);
		}
	}
}
