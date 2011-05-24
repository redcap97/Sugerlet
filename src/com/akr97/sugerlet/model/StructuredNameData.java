package com.akr97.sugerlet.model;

import com.akr97.sugerlet.util.StringUtil;

public class StructuredNameData {
	public long rawContactId;
	public String displayName;
	public String givenName;
	public String familyName;
	public String phoneticGivenName;
	public String phoneticFamilyName;
	
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
		return getJapaneseStyleName(familyName, givenName);
	}
	
	public String getPhoneticName(){
		return getJapaneseStyleName(phoneticFamilyName, phoneticGivenName);
	}
	
	public String getJapaneseStyleName(String familyName, String givenName){
		if(givenName == null){
			return StringUtil.toNonNullString(familyName);
		}else if(familyName == null){
			return StringUtil.toNonNullString(givenName);
		}else{
			return familyName + " " + givenName;
		}
	}
}
