package com.akr97.sugerlet.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;

import com.akr97.sugerlet.*;

public class MissingPhotoData extends PhotoData {
	private Context ctx;

	static Bitmap cache = null;

	public MissingPhotoData(Context ctx) {
		super(null);
		this.ctx = ctx;
	}

	@Override
	public Bitmap getBitmap(){
		if(cache == null){
			cache = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.contact);
		}	
		return cache; 
	}
}
