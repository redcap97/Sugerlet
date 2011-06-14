package com.akr97.sugerlet;

import java.util.ArrayList;

import com.akr97.sugerlet.model.StructuredNameData;

import android.content.Context;
import android.content.Intent;

public abstract class ContactsGetter {
	protected Context context;
	protected Intent intent;
	
	public ContactsGetter(Context context, Intent intent){
		this.context = context;
		this.intent = intent;
	}
	
	public abstract String getTitle();
	public abstract ArrayList<StructuredNameData> getStructuredNames();
}