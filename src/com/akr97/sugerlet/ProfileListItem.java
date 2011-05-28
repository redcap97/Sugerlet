package com.akr97.sugerlet;

import android.content.Context;
import android.view.View;

public abstract class ProfileListItem {
	protected Context context;
	protected Type type;
	
	public enum Type {
		HEADER,
		DATA
	}
	
	public ProfileListItem(Context context, Type type){
		this.context = context;
		this.type = type;
	}
	
	public Type getType(){
		return type;
	}
	
	public abstract View getView(View convertView);
}