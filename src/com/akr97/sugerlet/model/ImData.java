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

public class ImData {
	public final String data;
	public final int type;
	public final String label;
	public final int protocol;
	public final String customProtocol;

	public ImData(String data, int type, String label, 
			int protocol, String customProtocol){
		this.data = data;
		this.type = type;
		this.label = label;
		this.protocol = protocol;
		this.customProtocol = customProtocol;
	}

	@Override
	public String toString(){
		return String.format("data: %s, type: %d, label: %s," 
					+ " protocol: %s, customProtocol: %s",
				data, type, label, protocol, customProtocol);
	}
}
