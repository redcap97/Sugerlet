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

public class StructuredPostalData {
	public final String formattedAddress;
	public final int type;
	public final String label;
	public final String street;
	public final String pobox;
	public final String neighborhood;
	public final String city;
	public final String region;
	public final String postcode;
	public final String country;

	public StructuredPostalData(String formattedAddress, int type, String label, 
			String street, String pobox, String neighborhood, String city, 
			String region, String postcode, String country){
		this.formattedAddress = formattedAddress;
		this.type = type;
		this.label = label;
		this.street = street;
		this.pobox = pobox;
		this.neighborhood = neighborhood;
		this.city = city;
		this.region = region;
		this.postcode = postcode;
		this.country = country;
	}

	@Override
	public String toString(){
		return String.format("formattedAddress: %s, type: %d, label: %s, street: %s," +
					" pobox: %s, neighborhood: %s, city: %s," +
					" region: %s, postcode: %s, country: %s",
				formattedAddress, type, label, street, pobox, 
				neighborhood, city, region, postcode, country);
	}
}
