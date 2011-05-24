package com.akr97.sugerlet.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PhotoData {
	public byte[] bytes;
	
	public PhotoData(byte[] bytes){
		this.bytes = bytes;
	}
	
	public Bitmap getBitmap(){
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
	}
}
