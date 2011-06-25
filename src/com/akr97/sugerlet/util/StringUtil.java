package com.akr97.sugerlet.util;

public class StringUtil {
	public static String toNonNull(String str){
		return str == null ? "" : str;
	}
}