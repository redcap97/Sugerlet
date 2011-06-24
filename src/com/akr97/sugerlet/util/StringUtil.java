package com.akr97.sugerlet.util;

public class StringUtil {
	public static String toNonNullString(String str){
		return str == null ? "" : str;
	}

	public static String toZenkakuHiragana(String s) {
		StringBuffer sb = new StringBuffer(s);
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (c >= 'ァ' && c <= 'ン') {
				sb.setCharAt(i, (char)(c - 'ァ' + 'ぁ'));
			} else if (c == 'ヵ') {
				sb.setCharAt(i, 'か');
			} else if (c == 'ヶ') {
				sb.setCharAt(i, 'け');
			} else if (c == 'ヴ') {
				sb.setCharAt(i, 'う');
				sb.insert(i + 1, '゛');
				i++;
			}
		}
		return sb.toString();
	}
}