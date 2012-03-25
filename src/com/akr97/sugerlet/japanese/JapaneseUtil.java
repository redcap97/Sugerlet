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
