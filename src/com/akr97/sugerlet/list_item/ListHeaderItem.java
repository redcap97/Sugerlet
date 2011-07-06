package com.akr97.sugerlet.list_item;

import com.akr97.sugerlet.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ListHeaderItem extends ListItem {
	private final Context context;
	private final String title;

	private static final int KEY = R.string.key_list_header_item;

	public ListHeaderItem(Context context, String title) {
		super(Type.HEADER);
		
		this.context = context;
		this.title = title;
	}

	@Override
	public View getView(View convertView) {
		ViewHolder holder = getViewHolder(convertView);

		if(convertView == null || holder == null){
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.list_header_item, null);

			holder = new ViewHolder();
			holder.title = (TextView)convertView.findViewById(R.id.title);

			convertView.setTag(KEY, holder);
		}
		holder.title.setText(title);

		return convertView;
	}

	private ViewHolder getViewHolder(View convertView){
		if(convertView != null){
			return (ViewHolder)convertView.getTag(KEY);
		}
		return null;
	}

	static class ViewHolder {
		TextView title;
	}
}
