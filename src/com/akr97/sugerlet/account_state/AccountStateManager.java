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
import android.content.Context;
import android.accounts.AccountManager;
import android.accounts.Account;

import com.akr97.sugerlet.model.*;

public class AccountStateManager {
	private ArrayList<AccountState> states = new ArrayList<AccountState>();
	private Context context;

	static final String TAG = "com.akr97.sugerlet.AccountChanger";

	public AccountStateManager(Context ctx){
		this(ctx, true);
	}

	public AccountStateManager(Context ctx, boolean defaultState){
		this.context = ctx;
		addStatesFromAccountManager(defaultState);
		addStatesFromSettingsModel(defaultState);
	}

	public ArrayList<AccountState> getStates(){
		return new ArrayList<AccountState>(states);
	}

	public ArrayList<AccountState> getEnabledStates(){
		ArrayList<AccountState> enabledStates = new ArrayList<AccountState>();
		for(AccountState state : states){
			if(state.isEnabled()){
				enabledStates.add(state);
			}
		}
		return enabledStates;
	}

	public boolean isIncluded(Account account){
		for(AccountState s : states){
			if(account.equals(s.getAccount())){
				return true;
			}
		}
		return false;
	}

	public void update(AccountState state){
		Account account = state.getAccount();
		for(AccountState s : states){
			if(account.equals(s.getAccount())){
				s.setEnabled(state.isEnabled());
				return;
			}
		}
	}

	public static boolean canLoad(Context context){
		AccountStateStore store = new AccountStateStore(context);
		return store.isInitialized();
	}

	public void load(){
		AccountStateStore store = new AccountStateStore(context);
		for(AccountState state : store.get()){
			update(state);
		}
	}

	public void save(){
		AccountStateStore store = new AccountStateStore(context);
		store.put(states);
	}

	private void addStatesFromAccountManager(boolean defaultState){
		Account[] accounts = AccountManager.get(context).getAccounts();
		for(Account account : accounts){
			addState(account, defaultState);
		}
	}

	private void addStatesFromSettingsModel(boolean defaultState){
		SettingsDao settingsDao = new SettingsDao(context);
		for(SettingsData settings : settingsDao.getAll()){
			addState(settings.getAccount(), defaultState);
		}
	}

	private void addState(Account account, boolean enabled){
		if(!isIncluded(account)){
			states.add(new AccountState(account, enabled));
		}
	}
}
