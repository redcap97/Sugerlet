package com.akr97.sugerlet;

import java.util.ArrayList;
import java.util.Map;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AccountStateStore {
	private SharedPreferences pref;
	
	static final String PREF_FILE_NAME = "account_states";
	static final String KEY_IS_INITIALIZED = "Config.isInitialized";
	
	public AccountStateStore(Context context){
		this.pref = context.getSharedPreferences(PREF_FILE_NAME, Activity.MODE_PRIVATE);
	}
	
	public boolean isInitialized(){
		return pref.getBoolean(KEY_IS_INITIALIZED, false);
	}
	
	public void put(ArrayList<AccountState> states){
		SharedPreferences.Editor editor = pref.edit();
		editor.clear();
		editor.putBoolean(KEY_IS_INITIALIZED, true);
		for(AccountState state : states){
			editor.putBoolean(buildAccountKey(state.getAccount()), state.isEnabled());
		}
		editor.commit();
	}
	
	public ArrayList<AccountState> get(){
		ArrayList<AccountState> states = new ArrayList<AccountState>();	
		Map<String, ?> map = pref.getAll();
		for(String key : map.keySet()){
			if(key.equals(KEY_IS_INITIALIZED)){
				continue;
			}
			
			Account account = parseAccountKey(key);
			states.add(new AccountState(account, (Boolean)map.get(key)));
		}
		return states;
	}
	
	private void reset(){
		SharedPreferences.Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}
	
	private String buildAccountKey(Account account){
		return account.name + "\0" + account.type;
	}
	
	private Account parseAccountKey(String key){
		String[] tokens = key.split("\0");
		if(tokens.length != 2){
			reset();
			throw new RuntimeException("Account Key is not valid.");
		}
		return new Account(tokens[0], tokens[1]);
	}
}