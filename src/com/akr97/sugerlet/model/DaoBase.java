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

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;

public abstract class DaoBase<E> {
	protected final Context context;

	public DaoBase(Context context){
		this.context = context;
	}

	public ContentResolver getContentResolver(){
		return context.getContentResolver();
	}

	public Resources getResources(){
		return context.getResources();
	}

	public abstract E extract(Cursor c);

	public E readRow(Cursor c){
		E result = null;
		try{
			if(c.moveToFirst()){
				result = extract(c);
			}
		}finally{
			c.close();
		}
		return result;
	}

	public ArrayList<E> readRows(Cursor c){
		ArrayList<E> results = new ArrayList<E>();
		try{
			if(c.moveToFirst()){
				do {
					results.add(extract(c));
				}while(c.moveToNext());
			}
		}finally{
			c.close();
		}
		return results;
	}
}