/*
Copyright (C) 2012 Akira Midorikawa

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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
