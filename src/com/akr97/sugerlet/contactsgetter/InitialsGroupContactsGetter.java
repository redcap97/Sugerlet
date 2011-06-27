package com.akr97.sugerlet.contactsgetter;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.content.Intent;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.japanese.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.util.*;

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
		
		StructuredNameModel model = new StructuredNameModel(context);
		NormalizedNameList list =  NormalizedNameList.fromStructuredNames(model.getAll());
		return list.filter(params.initialsGroup).sort().extract();
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
	
	static class NormalizedNameList {
		private final ArrayList<NormalizedName> list;
		
		public NormalizedNameList(ArrayList<NormalizedName> list){
			this.list = list;
		}
		
		public NormalizedNameList filter(char initialsGroup){
			ArrayList<NormalizedName> results = new ArrayList<NormalizedName>();
			InitialsGroupSelector selector = new InitialsGroupSelector();
			for(NormalizedName name : list){
				if(selector.select(name.get()) == initialsGroup){
					results.add(name);
				}
			}
			return new NormalizedNameList(results);
		}
		
		public NormalizedNameList sort(){
			ArrayList<NormalizedName> results = new ArrayList<NormalizedName>(list);
			Collections.sort(results);
			return new NormalizedNameList(results);
		}
		
		public ArrayList<StructuredNameData> extract(){
			ArrayList<StructuredNameData> results = new ArrayList<StructuredNameData>();
			for(NormalizedName name : list){
				results.add(name.getEntity());
			}
			return results;
		}
		
		public static NormalizedNameList fromStructuredNames(ArrayList<StructuredNameData> names){
			ArrayList<NormalizedName> results = new ArrayList<NormalizedName>();
			for(StructuredNameData sn : names){
				results.add(new NormalizedName(sn));
			}			
			return new NormalizedNameList(results);
		}
	}

	static class NormalizedName implements Comparable<NormalizedName>{
		private final StructuredNameData entity;
		private final String value;

		public NormalizedName(StructuredNameData sn){
			this.entity = sn;
			this.value = normalize(sn);
		}

		public String get(){
			return value;
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
			
			return JapaneseUtil.toZenkakuHiragana(name.toUpperCase());
		}

		@Override
		public int compareTo(NormalizedName another) {
			return this.get().compareTo(another.get());
		}
	}
}
