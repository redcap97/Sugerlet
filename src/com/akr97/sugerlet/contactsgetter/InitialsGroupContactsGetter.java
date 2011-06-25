package com.akr97.sugerlet.contactsgetter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.util.*;
import com.akr97.sugerlet.util.japanese.*;

public class InitialsGroupContactsGetter extends ContactsGetter {
	private final Intent intent;
	
	public InitialsGroupContactsGetter(Context context, Intent intent){
		super(context);
		this.intent = intent;
	}

	@Override
	public String getTitle() {
		return context.getString(R.string.initials_group);
	}

	@Override
	public ArrayList<StructuredNameData> getStructuredNames(){
		Parameter params = new Parameter();
		ArrayList<StructuredNameData> results = new ArrayList<StructuredNameData>();
		
		InitialsGroupSelector selector = new InitialsGroupSelector();
		StructuredNameModel model = new StructuredNameModel(context);
		for(StructuredNameData sn : model.getAll()){
			NormalizedName name = new NormalizedName(sn);
			
			char initialsGroup = selector.select(name.get());
			if(initialsGroup == params.initialsGroup){
				results.add(name.getEntity());
			}
		}
		return results;
	}
	
	public static Intent getIntent(Context context, char initialsGroup){
		Intent intent = new Intent(context, ContactListActivity.class);
		intent.putExtra(context.getString(R.string.key_initials_group), initialsGroup);
		return intent;
	}
	
	class Parameter{
		public final char initialsGroup;
		
		public Parameter(){
			this.initialsGroup = this.getInitialsGroup();
		}
		
		public char getInitialsGroup(){
			char initialsGroup = intent.getCharExtra(context.getString(R.string.key_initials_group), '\0');
			if(initialsGroup == '\0'){
				throw new RuntimeException("InitialsGroup is not found.");
			}
			return initialsGroup;
		}
	}
	
	static class NormalizedName{
		private final StructuredNameData entity;
		private final String name;

		public NormalizedName(StructuredNameData sn){
			this.entity = sn;
			this.name = normalize(sn);
		}
		
		public String get(){
			return name;
		}
		
		public StructuredNameData getEntity(){
			return entity;
		}
		
		private String normalize(StructuredNameData sn){
			String name = (StringUtil.toNonNull(sn.phoneticFamilyName) + 
				StringUtil.toNonNull(sn.phoneticGivenName)).trim();
			
			if(name.length() == 0){
				name = StringUtil.toNonNull(sn.familyName) +
					StringUtil.toNonNull(sn.givenName);
			}
			
			return StringUtil.toZenkakuHiragana(name.toUpperCase());
		}
	}
}
