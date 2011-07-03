package com.akr97.sugerlet.listitem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.akr97.sugerlet.*;

public class ListContentItem extends ListItem {
	private final String content;
	private final Intent intent;

	private static final int KEY = R.string.key_list_content_item;

	public ListContentItem(Context context, String content, Intent intent){
		super(context, Type.CONTENT);
		this.content = content;
		this.intent = intent;
	}
	
	public ListContentItem(Context context, String content){
		super(context, Type.CONTENT);
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
