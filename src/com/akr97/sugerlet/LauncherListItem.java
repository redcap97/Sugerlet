package com.akr97.sugerlet;

import android.content.Context;
import android.view.View;

public abstract class LauncherListItem {
	protected final Context context;
	protected final Type type;
	
	public enum Type {
		HEADER,
		INTENT
	}
	
	public LauncherListItem(Context context, Type type){
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
