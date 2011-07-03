package com.akr97.sugerlet.model;

public class NoteData {
	public final String data;

	public NoteData(String data){
		this.data = data;
	}

	@Override
	public String toString(){
		return String.format("note: %s", data);
	}
}
