package com.akr97.sugerlet.util;

import com.akr97.sugerlet.japanese.JapaneseUtil;
import com.akr97.sugerlet.model.StructuredNameData;

public class NormalizedFullName extends NormalizedName {
	public NormalizedFullName(StructuredNameData sn) {
		super(sn);
	}

	@Override
	protected String normalize(StructuredNameData sn){
		String name = (StringUtil.toNonNull(sn.phoneticFamilyName) +
				StringUtil.toNonNull(sn.phoneticGivenName)).trim();

		name += (StringUtil.toNonNull(sn.familyName) +
				StringUtil.toNonNull(sn.givenName)).trim();

		return JapaneseUtil.normalize(name);
	}
}
