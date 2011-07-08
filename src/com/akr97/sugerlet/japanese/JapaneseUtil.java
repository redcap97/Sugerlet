package com.akr97.sugerlet.japanese;

import android.text.TextUtils;

import com.akr97.sugerlet.util.*;

public class JapaneseUtil {
	public static String toZenkakuHiragana(String s) {
		s = HankakuKatakanaToZenkakuKatakana.hankakuKatakanaToZenkakuKatakana(s);
		return ZenkakuKatakanaToZenkakuHiragana.zenkakuHiraganaToZenkakuKatakana(s);
	}

	public static String getName(String familyName, String givenName){
		if(TextUtils.isEmpty(givenName)){
			return StringUtil.toNonNull(familyName);
		}else if(TextUtils.isEmpty(familyName)){
			return StringUtil.toNonNull(givenName);
		}else{
			return familyName + " " + givenName;
		}
	}

	public static String normalize(String s){
		return JapaneseUtil.toZenkakuHiragana(s.toUpperCase());
	}
}
