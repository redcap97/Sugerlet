package com.akr97.sugerlet;

import android.content.Context;

public class AccountStateManagerFactory {
	static AccountStateManager accountChanger;
	
	public static AccountStateManager create(Context ctx){
		if(accountChanger == null){
			accountChanger = new AccountStateManager(ctx);
		}
		return accountChanger;
	}
}