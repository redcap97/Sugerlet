package com.akr97.sugerlet.contactsgetter;

import java.util.ArrayList;

import android.content.Context;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.util.*;

public class InitialsGroupContactsGetter extends ContactsGetter {
	public InitialsGroupContactsGetter(Context context){
		super(context);
	}

	@Override
	public String getTitle() {
		return context.getString(R.string.initials_group);
	}

	@Override
	public ArrayList<StructuredNameData> getStructuredNames(){
		ArrayList<StructuredNameData> results = new ArrayList<StructuredNameData>();
		
		InitialsGroupSelector selector = new InitialsGroupSelector();
		StructuredNameModel model = new StructuredNameModel(context);
		for(StructuredNameData sn : model.getAll()){
			NormalizedName name = new NormalizedName(sn);
			
			Character initialsGroup = selector.select(name.get());
			
			if(initialsGroup != null && initialsGroup.equals('あ')){
				results.add(name.getEntity());
			}
		}

		return results;
	}
	
	static class NormalizedName{
		private final StructuredNameData entity;
		private final String name;

		public NormalizedName(StructuredNameData sn){
			this.entity = sn;
			this.name = buildName(sn);
		}
		
		public String get(){
			return name;
		}
		
		public StructuredNameData getEntity(){
			return entity;
		}
		
		private String buildName(StructuredNameData sn){
			String name = (StringUtil.toNonNull(sn.phoneticFamilyName) + 
				StringUtil.toNonNull(sn.phoneticGivenName)).trim();
			
			if(name.length() == 0){
				name = StringUtil.toNonNull(sn.familyName) +
					StringUtil.toNonNull(sn.givenName);
			}
			
			return StringUtil.toZenkaku(name.toUpperCase());
		}
	}
}
