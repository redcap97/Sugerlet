package com.akr97.sugerlet;

import java.util.ArrayList;

import android.content.Context;

import com.akr97.sugerlet.model.*;

public abstract class ContactsGetter {
	protected Context context;
	
	public ContactsGetter(Context context){
		this.context = context;
	}
	
	public abstract String getTitle();
	public abstract ArrayList<StructuredNameData> getStructuredNames();
}