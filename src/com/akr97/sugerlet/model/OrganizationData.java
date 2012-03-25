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

package com.akr97.sugerlet.model;

import com.akr97.sugerlet.util.StringUtil;

import android.text.TextUtils;

public class OrganizationData {
	public final String company;
	public final int type;
	public final String label;
	public final String title;

	public OrganizationData(String company, int type, String label, String title){
		this.company = company;
		this.type = type;
		this.label = label;
		this.title = title;
	}
	
	public String getDisplayName(){
		if(TextUtils.isEmpty(title)){
			return StringUtil.toNonNull(company);
		}else if(TextUtils.isEmpty(company)){
			return StringUtil.toNonNull(title);
		}else{
			return company + "\n" + title;
		}
	}

	@Override
	public String toString(){
		return String.format("company: %s, type: %s, label: %s, title: %s",
				company, type, label, title);
	}
}
