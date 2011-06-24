package com.akr97.sugerlet.util;

import java.util.HashMap;

public class InitialsGroupSelector {
	static final char[][] INITIALS_GROUPS = {
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

	private HashMap<Character, Character> groupMap = new HashMap<Character, Character>();

	public InitialsGroupSelector(){
		this.groupMap = createGroupMap();
	}

	public Character select(String s){
		Character result = null;
		if(s.length() != 0){
			result = groupMap.get(s.charAt(0));
		}
		return result == null ? '\0' : result;
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
}
