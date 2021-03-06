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

package com.akr97.sugerlet.list_item;

import android.view.View;

public abstract class ListItem {
	protected final Type type;

	public enum Type {
		HEADER,
		CONTENT,
		CUSTOM
	}

	public ListItem(Type type){
		this.type = type;
	}

	public Type getType(){
		return type;
	}

	public boolean isHeader(){
		return (type == Type.HEADER);
	}

	public void onClick(View view){
	}

	public abstract View getView(View convertView);
}
