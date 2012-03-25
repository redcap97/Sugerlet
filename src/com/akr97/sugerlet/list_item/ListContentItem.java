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

package com.akr97.sugerlet.list_item;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.akr97.sugerlet.*;

public class ListContentItem extends ListItem {
	private final Context context;
	private final String content;
	private final Intent intent;

	private static final int KEY = R.string.key_list_content_item;

	public ListContentItem(Context context, String content, Intent intent){
		super(Type.CONTENT);
		
		this.context = context;
		this.content = content;
		this.intent = intent;
	}
	
	public ListContentItem(Context context, String content){
		super(Type.CONTENT);
		
		this.context = context;
		this.content = content;
		this.intent = null;
	}

	@Override
	public View getView(View convertView){
		ViewHolder holder = getViewHolder(convertView);

		if(convertView == null || holder == null){
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.list_content_item, null);

			holder = new ViewHolder();
			holder.content = (TextView)convertView.findViewById(R.id.textView);

			convertView.setTag(KEY, holder);
		}
		holder.content.setText(content);

		return convertView;
	}

	@Override
	public void onClick(View view){
		if(intent != null){
			context.startActivity(intent);
		}
	}

	private ViewHolder getViewHolder(View convertView){
		if(convertView != null){
			return (ViewHolder)convertView.getTag(KEY);
		}
		return null;
	}

	static class ViewHolder {
		TextView content;
	}
}
