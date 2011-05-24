package com.akr97.sugerlet.model;

public class PhotoData {
	public long id;
	public byte[] bytes;
	
	public PhotoData(long id, byte[] bytes){
		this.id = id;
		this.bytes = bytes;
	}
}
