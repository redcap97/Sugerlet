package com.akr97.sugerlet.model;

public class StructuredPostalData {
	public String formattedAddress;
	public int type;
	public String label;
	public String street;
	public String pobox;
	public String neighborhood;
	public String city;
	public String region;
	public String postcode;
	public String country;
	
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
