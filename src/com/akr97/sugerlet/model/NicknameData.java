package com.akr97.sugerlet.model;

public class NicknameData {
	public final String name;
	public final int type;
	public final String label;

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
