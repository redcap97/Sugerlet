package com.akr97.sugerlet.model;

import java.util.Vector;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;

public abstract class ModelBase<E> {
	private final Context context;
	
	public ModelBase(Context context){
		this.context = context;
	}
	
	public Context getContext(){
		return context;
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
	
	public Vector<E> readRows(Cursor c){
		Vector<E> results = new Vector<E>();
		
		if(c.moveToFirst()){
			do {
				results.add(extract(c));
			}while(c.moveToNext());
		}
		c.close();
		
		return results;
	}
}