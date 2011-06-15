package com.akr97.sugerlet;

import java.util.ArrayList;
import android.content.Context;
import android.accounts.AccountManager;
import android.accounts.Account;

import com.akr97.sugerlet.model.*;

public class AccountStateManager {
	private ArrayList<AccountState> states = new ArrayList<AccountState>();
	
	static final String TAG = "com.akr97.sugerlet.AccountChanger";
	
	public AccountStateManager(Context ctx){
		addStatesFromAccountManager(ctx);
		addStatesFromSettingsModel(ctx);
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
	
	public boolean hasFilters(){
		for(AccountState s : states){
			if(!s.isEnabled()){
				return true;
			}
		}
		return false;
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

	private void addStatesFromAccountManager(Context ctx){
        Account[] accounts = AccountManager.get(ctx).getAccounts();
        for(Account account : accounts){
        	addState(account);
        }
	}
	
	private void addStatesFromSettingsModel(Context ctx){
        SettingsModel settingsModel = new SettingsModel(ctx);
        for(SettingsData settings : settingsModel.getAll()){
        	addState(settings.getAccount());
        }
	}
	
	private void addState(Account account){
		if(!isIncluded(account)){
			states.add(new AccountState(account));
		}
	}
}
