package com.akr97.sugerlet.util;

import android.accounts.Account;

public class AccountUtil {
	public static String getHeading(Account account){
		return String.format("%s (%s)", account.name, account.type);
	}
}
