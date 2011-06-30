package com.akr97.sugerlet.util;

public class StringUtil {
	public static String toNonNull(String str){
		return str == null ? "" : str;
	}

	public static String[] splitLast(String target, String pattern){
		int index = target.lastIndexOf(pattern);
		if(index == -1){
			throw new RuntimeException("Pattern is not found.");
		}

		return new String[]{
				target.substring(0, index),
				target.substring(index+1)
		};
	}
}