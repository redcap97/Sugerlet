package com.akr97.sugerlet;

import java.util.Iterator;
import java.util.Vector;
import android.content.Context;
import android.accounts.AccountManager;
import android.accounts.Account;
import android.util.Log;

public class AccountStateManager implements Iterable<AccountStateManager.State> {
	private Vector<State> states = new Vector<State>();
	
	static final String TAG = "com.akr97.sugerlet.AccountChanger";
	
	public AccountStateManager(Context ctx){
        Account[] accounts = AccountManager.get(ctx).getAccounts();
        for(Account account : accounts){
        	this.states.add(new State(account.name, account.type));
        }
	}
	
	@Override
	public Iterator<State> iterator() {
		return new StateIterator(this.states);
	}
	
	public boolean hasFilter(){
		for(State s : this.states){
			if(s.isDisabled()){
				return true;
			}
		}
		return false;
	}
	
	public class StateIterator implements Iterator<State>{
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
	
	public class State {
		private String name;
		private String type;
		private boolean enabled = true;
		
		public State(String name, String type){
			this.name = name;
			this.type = type;
		}
		
		public String getName(){
			return name;
		}
		
		public String getType(){
			return type;
		}
		
		public boolean isEnabled(){
			return enabled;
		}
		
		public boolean isDisabled(){
			return !isEnabled();
		}
		
		public void enable(){
			enabled = true;
		}
		
		public void disable(){
			enabled = false;
		}
		
		@Override
		public String toString(){
			return String.format("name: %s, type: %s, enabled: %b",
					this.name, this.type, this.enabled);
		}
	}
}
