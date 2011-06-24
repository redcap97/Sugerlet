package com.akr97.sugerlet.util;

import com.akr97.sugerlet.util.japanese.*;

public class StringUtil {
	public static String toNonNullString(String str){
		return str == null ? "" : str;
	}

	public static String toZenkakuHiragana(String s) {
		return ZenkakuKatakanaToZenkakuHiragana.zenkakuHiraganaToZenkakuKatakana(s);
	}
	
	public static String toZenkakuKatakana(String s){
		return HankakuKatakanaToZenkakuKatakana.hankakuKatakanaToZenkakuKatakana(s);
	}
	
	public static String toZenkaku(String s){
		return toZenkakuHiragana(toZenkakuKatakana(s));
	}
}