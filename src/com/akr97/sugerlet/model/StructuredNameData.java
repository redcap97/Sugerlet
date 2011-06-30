package com.akr97.sugerlet.model;

import com.akr97.sugerlet.japanese.*;

public class StructuredNameData {
	public final long rawContactId;
	public final String displayName;
	public final String givenName;
	public final String familyName;
	public final String phoneticGivenName;
	public final String phoneticFamilyName;

	public StructuredNameData(long rawContactId, String displayName, String givenName, String familyName,
			String phoneticGivenName, String phoneticFamilyName){
		this.rawContactId = rawContactId;
		this.displayName = displayName;
		this.givenName = givenName;
		this.familyName = familyName;
		this.phoneticGivenName = phoneticGivenName;
		this.phoneticFamilyName = phoneticFamilyName;
	}

	public String getName(){
		return JapaneseUtil.getName(familyName, givenName);
	}

	public String getPhoneticName(){
		return JapaneseUtil.getName(phoneticFamilyName, phoneticGivenName);
	}

	public String getPhoneticName(String defaultValue){
		String phoneticName = getPhoneticName();

		if(phoneticName.length() != 0){
			return phoneticName;
		}else{
			return defaultValue;
		}
	}
}
