package com.akr97.sugerlet.contactsgetter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

import com.akr97.sugerlet.*;
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
}
