package com.akr97.sugerlet;

import android.content.Context;

public class AccountChangerFactory {
	static AccountChanger accountChanger;
	
	public static AccountChanger create(Context ctx){
		if(accountChanger == null){
			accountChanger = new AccountChanger(ctx);
		}
		return accountChanger;
	}
}