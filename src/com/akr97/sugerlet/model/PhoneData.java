package com.akr97.sugerlet.model;

import android.net.Uri;

public class PhoneData {
	public final long id;
	public final String number;
	public final int type;
	public final String label;

	public PhoneData(long id, String number, int type, String label){
		this.id = id;
		this.number = number;
		this.type = type;
		this.label = label;
	}

	public Uri getTelephoneUri(){
		return Uri.parse("tel:" + number);
	}
	
	public Uri getSmsUri(){
		return Uri.parse("smsto:" + number);
	}

	@Override
	public String toString(){
		return String.format("id: %d, number: %s, type: %d, label: %s", 
				this.id, this.number, this.type, this.label);
	}
}
