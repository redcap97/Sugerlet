package com.akr97.sugerlet.model;

import com.akr97.sugerlet.japanese.*;
import com.akr97.sugerlet.util.StringUtil;

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

	public String getName(String defaultValue){
		String name = JapaneseUtil.getName(familyName, givenName);
		return StringUtil.findNonEmptyElement(name, defaultValue);
	}

	public String getPhoneticName(String defaultValue){
		String name = JapaneseUtil.getName(phoneticFamilyName, phoneticGivenName);
		return StringUtil.findNonEmptyElement(name, defaultValue);
	}
}
