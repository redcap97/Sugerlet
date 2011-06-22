package com.akr97.sugerlet.util;

import android.accounts.Account;

public class AccountUtil {
	public static String getHeading(Account account){
		if(account.name.equals(account.type)){
			return account.name;
		}else{
			return String.format("%s (%s)", account.name, account.type);
		}
	}
}
