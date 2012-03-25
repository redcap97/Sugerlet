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

public class ZenkakuKatakanaToZenkakuHiragana {
	/*
	 * ぁあぃいぅうぇえぉおかがきぎくぐけげこごさざしじすずせぜそぞ
	 * ただちぢっつづてでとどなにぬねのはばぱひびぴふぶぷへべぺほぼぽ
	 * まみむめもゃやゅゆょよらりるれろゎわゐゑをん
	 * 
	 * ァアィイゥウェエォオカガキギクグケゲコゴサザシジスズセゼソゾ
	 * タダチヂッツヅテデトドナニヌネノハバパヒビピフブプヘベペホボポ
	 * マミムメモャヤュユョヨラリルレロヮワヰヱヲンヴヵヶ
	 */
	public static String zenkakuHiraganaToZenkakuKatakana(String s) {
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
