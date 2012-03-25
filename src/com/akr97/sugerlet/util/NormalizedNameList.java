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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import android.text.TextUtils;

import com.akr97.sugerlet.japanese.*;
import com.akr97.sugerlet.model.*;

public class NormalizedNameList implements Iterable<NormalizedName> {
	private final ArrayList<NormalizedName> names;

	public NormalizedNameList(ArrayList<NormalizedName> names){
		this.names = names;
	}

	public NormalizedNameList filter(char initialsGroup){
		ArrayList<NormalizedName> results = new ArrayList<NormalizedName>();
		InitialsGroupSelector selector = new InitialsGroupSelector();
		for(NormalizedName name : names){
			if(selector.select(name.get()) == initialsGroup){
				results.add(name);
			}
		}
		return new NormalizedNameList(results);
	}

	public NormalizedNameList filter(String pattern){
		if(TextUtils.isEmpty(pattern)){
			return new NormalizedNameList(names);
		}

		ArrayList<NormalizedName> results = new ArrayList<NormalizedName>();
		String normalizedPattern = JapaneseUtil.normalize(pattern);
		for(NormalizedName name : names){
			if(name.get().indexOf(normalizedPattern) != -1){
				results.add(name);
			}
		}
		return new NormalizedNameList(results);
	}

	public NormalizedNameList sort(){
		ArrayList<NormalizedName> results = new ArrayList<NormalizedName>(names);
		Collections.sort(results);
		return new NormalizedNameList(results);
	}

	public ArrayList<StructuredNameData> extract(){
		ArrayList<StructuredNameData> results = new ArrayList<StructuredNameData>();
		for(NormalizedName name : names){
			results.add(name.getEntity());
		}
		return results;
	}

	public int size(){
		return names.size();
	}

	@Override
	public Iterator<NormalizedName> iterator() {
		return new NormalizedNameIterator(names);
	}

	public static NormalizedNameList fromPhoneticNames(ArrayList<StructuredNameData> names){
		ArrayList<NormalizedName> results = new ArrayList<NormalizedName>();
		for(StructuredNameData sn : names){
			results.add(new NormalizedName(sn));
		}
		return new NormalizedNameList(results);
	}

	public static NormalizedNameList fromFullNames(ArrayList<StructuredNameData> names){
		ArrayList<NormalizedName> results = new ArrayList<NormalizedName>();
		for(StructuredNameData sn : names){
			results.add(new NormalizedFullName(sn));
		}
		return new NormalizedNameList(results);
	}

	public static class NormalizedNameIterator implements Iterator<NormalizedName>{
		private int pos = 0;
		private ArrayList<NormalizedName> names;

		public NormalizedNameIterator(ArrayList<NormalizedName> names){
			this.names = names;
		}

		@Override
		public boolean hasNext() {
			if(names.size() <= pos){
				return false;
			}
			return true;
		}

		@Override
		public NormalizedName next() {
			return names.get(pos++);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove() method is not implemented.");
		}
	}
}