package com.akr97.sugerlet.model;

import android.net.Uri;

public class WebsiteData {
	public final String url;
	public final int type;
	public final String label;
	
	public WebsiteData(String url, int type, String label){
		this.url = url;
		this.type = type;
		this.label = label;
	}
	
	public Uri getUri(){
		return Uri.parse(url);
	}
	
	@Override
	public String toString(){
		return String.format("url: %s, type: %d, label: %s", url, type, label);
	}
}
