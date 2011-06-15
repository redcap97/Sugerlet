package com.akr97.sugerlet;

import android.content.Context;

public class AccountStateManagerFactory {
	static AccountStateManager accountStateManager;
	
	public static AccountStateManager create(Context ctx){
		if(accountStateManager == null){
			AccountStateStore store = new AccountStateStore(ctx);
			
			if(store.isInitialized()){
				accountStateManager = new AccountStateManager(ctx, false);
				for(AccountState state : store.get()){
					accountStateManager.update(state);
				}
			}else{
				accountStateManager = new AccountStateManager(ctx);			
			}
		}
		return accountStateManager;
	}
}