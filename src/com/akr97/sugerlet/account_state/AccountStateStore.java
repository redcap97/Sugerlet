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

package com.akr97.sugerlet.account_state;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.akr97.sugerlet.util.*;

public class AccountStateStore {
	private SharedPreferences pref;

	static final String PREF_FILE_NAME = "account_states";
	static final String KEY_IS_INITIALIZED = "Config.isInitialized";
	static final String ACCOUNT_SEPARATOR = "$";

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

		for(Entry<String, ?> entry : map.entrySet()){
			String key = entry.getKey();
			if(!key.equals(KEY_IS_INITIALIZED)){
				Account account = parseAccountKey(key);
				boolean state = (Boolean)entry.getValue();

				states.add(new AccountState(account, state));
			}
		}

		return states;
	}

	private void reset(){
		SharedPreferences.Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}

	private String buildAccountKey(Account account){
		return account.name + ACCOUNT_SEPARATOR + account.type;
	}

	private Account parseAccountKey(String key){
		String[] tokens = null;
		try{
			tokens = StringUtil.splitLast(key, ACCOUNT_SEPARATOR);
		}catch(Exception e){
			reset();
			throw new RuntimeException("Account Key is not valid.");
		}
		return new Account(tokens[0], tokens[1]);
	}
}
