package com.akr97.sugerlet.listitem;

import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;

public class ListItemClickListener implements AdapterView.OnItemClickListener {
	private final ArrayList<ListItem> items;

	public ListItemClickListener(ArrayList<ListItem> items){
		this.items = items;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		ListItem item = items.get(position);
		item.onClick(view);
	}
}
