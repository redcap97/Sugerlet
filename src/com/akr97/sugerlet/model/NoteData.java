package com.akr97.sugerlet.model;

public class NoteData {
	public final String note;
	
	public NoteData(String note){
		this.note = note;
	}
	
	@Override
	public String toString(){
		return String.format("note: %s", note);
	}
}
