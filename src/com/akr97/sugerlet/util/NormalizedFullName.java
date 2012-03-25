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
