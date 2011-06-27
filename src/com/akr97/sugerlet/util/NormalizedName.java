package com.akr97.sugerlet.util;

import com.akr97.sugerlet.japanese.*;
import com.akr97.sugerlet.model.*;

public class NormalizedName implements Comparable<NormalizedName>{
	private final StructuredNameData entity;
	private final String value;

	public NormalizedName(StructuredNameData sn){
		this.entity = sn;
		this.value = normalize(sn);
	}

	public String get(){
		return value;
	}

	public StructuredNameData getEntity(){
		return entity;
	}

	private String normalize(StructuredNameData sn){
		String name = (StringUtil.toNonNull(sn.phoneticFamilyName) +
				StringUtil.toNonNull(sn.phoneticGivenName)).trim();

		if(name.length() == 0){
			name = StringUtil.toNonNull(sn.familyName) +
			StringUtil.toNonNull(sn.givenName);
		}

		return JapaneseUtil.toZenkakuHiragana(name.toUpperCase());
	}

	@Override
	public int compareTo(NormalizedName another) {
		return this.get().compareTo(another.get());
	}
}