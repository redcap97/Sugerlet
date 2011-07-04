package com.akr97.sugerlet.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PhotoData {
	public final byte[] bytes;

	public PhotoData(byte[] bytes){
		this.bytes = bytes;
	}

	public boolean isValid(){
		return (bytes != null);
	}

	public Bitmap getBitmap(){
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
	}
}
