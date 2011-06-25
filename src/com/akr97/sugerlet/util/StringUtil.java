package com.akr97.sugerlet.util;

import com.akr97.sugerlet.util.japanese.*;

public class StringUtil {
	public static String toNonNull(String str){
		return str == null ? "" : str;
	}

	public static String toZenkakuHiragana(String s) {
		s = HankakuKatakanaToZenkakuKatakana.hankakuKatakanaToZenkakuKatakana(s);
		return ZenkakuKatakanaToZenkakuHiragana.zenkakuHiraganaToZenkakuKatakana(s);
	}
}