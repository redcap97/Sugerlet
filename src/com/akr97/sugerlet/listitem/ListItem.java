package com.akr97.sugerlet.listitem;

import android.content.Context;
import android.view.View;

public abstract class ListItem {
	protected final Context context;
	protected final Type type;
	
	public enum Type {
		HEADER,
		CONTENT,
		INTENT,
		CUSTOM
	}
	
	public ListItem(Context context, Type type){
		this.context = context;
		this.type = type;
	}
	
	public Type getType(){
		return type;
	}
	
	public boolean isHeader(){
		return type == Type.HEADER;
	}
	
	public void onClick(View view){
	}
	
	public abstract View getView(View convertView);
}
