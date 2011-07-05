package com.akr97.sugerlet.model;

import com.akr97.sugerlet.util.StringUtil;

import android.text.TextUtils;

public class OrganizationData {
	public final String company;
	public final int type;
	public final String label;
	public final String title;

	public OrganizationData(String company, int type, String label, String title){
		this.company = company;
		this.type = type;
		this.label = label;
		this.title = title;
	}
	
	public String getDisplayName(){
		if(TextUtils.isEmpty(title)){
			return StringUtil.toNonNull(company);
		}else if(TextUtils.isEmpty(company)){
			return StringUtil.toNonNull(title);
		}else{
			return company + "\n" + title;
		}
	}

	@Override
	public String toString(){
		return String.format("company: %s, type: %s, label: %s, title: %s",
				company, type, label, title);
	}
}
