package com.akr97.sugerlet.japanese;

import com.akr97.sugerlet.util.*;

public class JapaneseUtil {
	public static String toZenkakuHiragana(String s) {
		s = HankakuKatakanaToZenkakuKatakana.hankakuKatakanaToZenkakuKatakana(s);
		return ZenkakuKatakanaToZenkakuHiragana.zenkakuHiraganaToZenkakuKatakana(s);
	}
	
	public static String getName(String familyName, String givenName){
		if(givenName == null || givenName.length() == 0){
			return StringUtil.toNonNull(familyName);
		}else if(familyName == null || familyName.length() == 0){
			return StringUtil.toNonNull(givenName);
		}else{
			return familyName + " " + givenName;
		}
	}
}
