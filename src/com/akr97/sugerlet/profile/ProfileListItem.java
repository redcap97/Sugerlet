package com.akr97.sugerlet.profile;

import android.content.Context;
import android.view.View;

public abstract class ProfileListItem {
	protected Context context;
	protected Type type;
	
	public enum Type {
		HEADER,
		CONTENT,
		PHONE,
		EMAIL,
		WEBSITE,
	}
	
	public ProfileListItem(Context context, Type type){
		this.context = context;
		this.type = type;
	}
	
	public Type getType(){
		return type;
	}
	
	public boolean isHeader(){
		return type == Type.HEADER;
	}
	
	public abstract View getView(View convertView);
	
	public void onClick(View view){
	}
}
