package com.akr97.sugerlet.model;

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

	@Override
	public String toString(){
		return String.format("company: %s, type: %s, label: %s, title: %s",
				company, type, label, title);
	}
}
