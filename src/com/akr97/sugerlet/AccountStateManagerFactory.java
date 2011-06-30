package com.akr97.sugerlet;

import android.content.Context;

public class AccountStateManagerFactory {
	static AccountStateManager accountStateManager;

	public static AccountStateManager create(Context ctx){
		if(accountStateManager == null){		
			if(AccountStateManager.canLoad(ctx)){
				accountStateManager = new AccountStateManager(ctx, false);
				accountStateManager.load();
			}else{
				accountStateManager = new AccountStateManager(ctx);			
			}
		}
		return accountStateManager;
	}
}