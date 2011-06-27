package com.akr97.sugerlet.util;

import java.util.ArrayList;
import java.util.Collections;

import com.akr97.sugerlet.japanese.*;
import com.akr97.sugerlet.model.*;

public class NormalizedNameList {
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