/*
Copyright (C) 2012 Akira Midorikawa

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.akr97.sugerlet.util;

import android.text.TextUtils;

public class StringUtil {
	public static String toNonNull(String str){
		return str == null ? "" : str;
	}

	public static String findNonEmptyElement(String... strings){
		for(String string : strings){
			if(!TextUtils.isEmpty(string)){
				return string;
			}
		}
		return "";
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