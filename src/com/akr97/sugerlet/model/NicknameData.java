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
