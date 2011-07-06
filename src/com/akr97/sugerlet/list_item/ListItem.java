package com.akr97.sugerlet.list_item;

import android.view.View;

public abstract class ListItem {
	protected final Type type;

	public enum Type {
		HEADER,
		CONTENT,
		CUSTOM
	}

	public ListItem(Type type){
		this.type = type;
	}

	public Type getType(){
		return type;
	}

	public boolean isHeader(){
		return (type == Type.HEADER);
	}

	public void onClick(View view){
	}

	public abstract View getView(View convertView);
}
