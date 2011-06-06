package com.akr97.sugerlet;

import java.util.Iterator;
import java.util.Vector;
import android.content.Context;
import android.accounts.AccountManager;
import android.accounts.Account;

import com.akr97.sugerlet.model.*;

public class AccountStateManager implements Iterable<AccountStateManager.State> {
	private Vector<State> states = new Vector<State>();
	
	static final String TAG = "com.akr97.sugerlet.AccountChanger";
	
	public AccountStateManager(Context ctx){
        Account[] accounts = AccountManager.get(ctx).getAccounts();
        for(Account account : accounts){
        	states.add(new State(account.name, account.type));
        }
        
        SettingsModel settingsModel = new SettingsModel(ctx);
        for(SettingsData settings : settingsModel.getAll()){
        	if(!isIncluded(settings.accountName, settings.accountType)){
        		states.add(new State(settings.accountName, settings.accountType));
        	}
        }
	}
	
	@Override
	public Iterator<State> iterator() {
		return new StateIterator(states);
	}
	
	public Vector<State> getEnabledStates(){
		Vector<State> enabledStates = new Vector<State>();
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
	
	public boolean isIncluded(String accountName, String accountType){
		for(State s : states){
			if(accountName.equals(s.getName()) && accountType.equals(s.getType())){
				return true;
			}
		}
		return false;
	}
	
	public void update(State state){
		for(State s : states){
			if(state.getName().equals(s.getName()) && state.getType().equals(s.getType())){
				s.setEnabled(state.isEnabled());
				return;
			}
		}
	}
	
	public static class StateIterator implements Iterator<State>{
		private int pos = 0;
		private Vector<State> states;
		
		public StateIterator(Vector<State> states){
			this.states = states;
		}
		
		@Override
		public boolean hasNext() {
			if(states.size() <= pos){
				return false;
			}
			return true;
		}

		@Override
		public State next() {
			return states.get(pos++);
		}

		@Override
		public void remove() {
		}
	}
	
	public static class State {		
		private Account account;
		private boolean enabled;
		
		public State(String name, String type){
			this.account = new Account(name, type);
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
