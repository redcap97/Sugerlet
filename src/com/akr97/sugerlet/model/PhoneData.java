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

import android.net.Uri;

public class PhoneData {
	public final long id;
	public final String number;
	public final int type;
	public final String label;

	public PhoneData(long id, String number, int type, String label){
		this.id = id;
		this.number = number;
		this.type = type;
		this.label = label;
	}

	public Uri getTelephoneUri(){
		return Uri.parse("tel:" + number);
	}
	
	public Uri getSmsUri(){
		return Uri.parse("smsto:" + number);
	}

	@Override
	public String toString(){
		return String.format("id: %d, number: %s, type: %d, label: %s", 
				this.id, this.number, this.type, this.label);
	}
}
