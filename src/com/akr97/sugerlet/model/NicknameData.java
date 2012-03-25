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

public class NicknameData {
	public final String data;
	public final int type;
	public final String label;

	public NicknameData(String data, int type, String label){
		this.data = data;
		this.type = type;
		this.label = label;
	}

	@Override
	public String toString(){
		return String.format("name: %s, type: %d, label: %s", data, type, label);
	}
}
