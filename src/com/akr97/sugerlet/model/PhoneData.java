package com.akr97.sugerlet.model;

public class PhoneData {
	public long id;
	public String number;
	public int type;
	public String label;
	
	public PhoneData(long id, String number, int type, String label){
		this.id = id;
		this.number = number;
		this.type = type;
		this.label = label;
	}
	
	@Override
	public String toString(){
		return String.format("id: %d, number: %s, type: %d, label: %s", 
				this.id, this.number, this.type, this.label);
	}
}
