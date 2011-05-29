package com.akr97.sugerlet.model;

public class NicknameData {
	public String name;
	public int type;
	public String label;

	public NicknameData(String name, int type, String label){
		this.name = name;
		this.type = type;
		this.label = label;
	}
	
	@Override
	public String toString(){
		return String.format("name: %s, type: %d, label: %s", name, type, label);
	}
}
