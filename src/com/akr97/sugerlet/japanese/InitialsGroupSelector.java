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

import java.util.HashMap;

public class InitialsGroupSelector {
	public static final char[] INITIALS_GROUP_NAMES = {
		'あ', 'か', 'さ', 'た', 'な', 'は', 'ま', 'や', 'ら', 'わ', 'A', '0', '~'
	};

	private static final char[][] INITIALS_GROUPS = {
		{'あ', 'ぁ', 'い', 'ぃ', 'う', 'ぅ', 'え', 'ぇ', 'お', 'ぉ'},
		{'か', 'が', 'き', 'ぎ', 'く', 'ぐ', 'け', 'げ', 'こ', 'ご'},
		{'さ', 'ざ', 'し', 'じ', 'す', 'ず', 'せ', 'ぜ', 'そ', 'ぞ'},
		{'た', 'だ', 'ち', 'ぢ', 'っ', 'つ', 'づ', 'て', 'で', 'と', 'ど'},
		{'な', 'に', 'ぬ', 'ね', 'の'},
		{'は', 'ば', 'ぱ', 'ひ', 'び', 'ぴ', 'ふ', 'ぶ', 'ぷ', 'へ', 'べ', 'ぺ', 'ほ', 'ぼ', 'ぽ'},
		{'ま', 'み', 'む', 'め', 'も'},
		{'や', 'ゃ', 'ゆ', 'ゅ', 'よ', 'ょ'},
		{'ら', 'り', 'る', 'れ', 'ろ'},
		{'わ', 'ゎ', 'ゐ', 'ゑ', 'を', 'ん'},
		{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'},
		{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}
	};

	private static final char GROUP_ALPHABET = 'A';
	private static final char GROUP_NUMBER = '0';
	private static final char GROUP_OTHER = '~';

	private static HashMap<Character, Character> groupMap = null;

	public InitialsGroupSelector(){
		if(groupMap == null){
			groupMap = createGroupMap();
		}
	}

	public char select(String s){
		Character result = null;
		if(s.length() != 0){
			result = groupMap.get(s.charAt(0));
		}
		return result == null ? GROUP_OTHER : result.charValue();
	}

	private HashMap<Character, Character> createGroupMap(){
		HashMap<Character, Character> map = new HashMap<Character, Character>();
		for(char[] initialsGroup : INITIALS_GROUPS){
			char group = initialsGroup[0];
			for(char letter : initialsGroup){
				map.put(letter, group);
			}
		}
		return map;
	}

	public static String getGroupName(char group){
		switch(group){
		case GROUP_ALPHABET:
			return "英字";
		case GROUP_NUMBER:
			return "数字";
		case GROUP_OTHER:
			return "その他";
		}
		return String.valueOf(group) + "行";
	}
}
