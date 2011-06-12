package com.akr97.sugerlet;

import java.util.ArrayList;
import android.content.Context;
import android.accounts.AccountManager;
import android.accounts.Account;

import com.akr97.sugerlet.model.*;

public class AccountStateManager {
	private ArrayList<State> states = new ArrayList<State>();
	
	static final String TAG = "com.akr97.sugerlet.AccountChanger";
	
	public AccountStateManager(Context ctx){
		addStatesFromAccountManager(ctx);
		addStatesFromSettingsModel(ctx);
	}
	
	public ArrayList<State> getStates(){
		return new ArrayList<State>(states);
	}
	
	public ArrayList<State> getEnabledStates(){
		ArrayList<State> enabledStates = new ArrayList<State>();
		for(State state : states){
			if(state.isEnabled()){
				enabledStates.add(state);
			}
		}
		return enabledStates;
	}
	
	public boolean hasFilters(){
		for(State s : states){
			if(!s.isEnabled()){
				return true;
			}
		}
		return false;
	}
	
	public boolean isIncluded(Account account){
		for(State s : states){
			if(account.equals(s.getAccount())){
				return true;
			}
		}
		return false;
	}
	
	public void update(State state){
		Account account = state.getAccount();
		for(State s : states){
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
			states.add(new State(account));
		}
	}
	
	public static class State {		
		private final Account account;
		private boolean enabled;
		
		public State(Account account){
			this.account = account;
			this.enabled = true;
		}
		
		public State(State state){
			this.account = new Account(state.getName(), state.getType());
			this.enabled = state.isEnabled();
		}
		
		public String getName(){
			return account.name;
		}
		
		public String getType(){
			return account.type;
		}
		
		public Account getAccount(){
			return account;
		}
		
		public String getHeading(){
			return String.format("%s (%s)", account.name, account.type);
		}
		
		public boolean isEnabled(){
			return enabled;
		}
		
		public void setEnabled(boolean enabled){
			this.enabled = enabled;
		}
		
		@Override
		public String toString(){
			return String.format("name: %s, type: %s, enabled: %b",
					account.name, account.type, enabled);
		}
	}
}
