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

	protected String normalize(StructuredNameData sn){
		String name = (StringUtil.toNonNull(sn.phoneticFamilyName) +
				StringUtil.toNonNull(sn.phoneticGivenName)).trim();

		if(name.length() == 0){
			name = StringUtil.toNonNull(sn.familyName) +
				StringUtil.toNonNull(sn.givenName);
		}

		return JapaneseUtil.normalize(name);
	}

	@Override
	public int compareTo(NormalizedName another) {
		return value.compareTo(another.value);
	}

	@Override
	public boolean equals(Object object){
		if((object != null) && (object instanceof NormalizedName)){
			NormalizedName another = (NormalizedName)object;
			return value.equals(another.value);
		}
		return false;
	}

	@Override
	public int hashCode(){
		return value.hashCode();
	}
}