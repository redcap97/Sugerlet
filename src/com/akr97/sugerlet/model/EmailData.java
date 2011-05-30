package com.akr97.sugerlet.model;

import android.net.Uri;

public class EmailData {
	public String address;
	public int type;
	public String label;
	
	public EmailData(String address, int type, String label){
		this.address = address;
		this.type = type;
		this.label = label;
	}
	
	public Uri getMailtoUri(){
		return Uri.parse("mailto:" + address);
	}
	
	@Override
	public String toString(){
		return String.format("address: %s, type: %s, label: %s",
				address, type, label);
	}
}
