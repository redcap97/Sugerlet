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
		if(c.moveToFirst()){
			result = extract(c);
		}
		c.close();

		return result;
	}

	public ArrayList<E> readRows(Cursor c){
		ArrayList<E> results = new ArrayList<E>();

		if(c.moveToFirst()){
			do {
				results.add(extract(c));
			}while(c.moveToNext());
		}
		c.close();

		return results;
	}
}