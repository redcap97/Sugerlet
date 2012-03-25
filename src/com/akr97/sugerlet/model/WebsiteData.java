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

public class WebsiteData {
	public final String url;
	public final int type;
	public final String label;

	public WebsiteData(String url, int type, String label){
		this.url = url;
		this.type = type;
		this.label = label;
	}

	public Uri getUri(){
		return Uri.parse(url);
	}

	@Override
	public String toString(){
		return String.format("url: %s, type: %d, label: %s", url, type, label);
	}
}
