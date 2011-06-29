package com.akr97.sugerlet.model;

public class EventData {
	public final String startDate;
	public final int type;
	public final String label;
	
	public EventData(String startDate, int type, String label){
		this.startDate = startDate;
		this.type = type;
		this.label = label;
	}
	
	@Override
	public String toString(){
		return String.format("startDate: %s, type: %s, label: %s",
				startDate, type, label);
	}
}
