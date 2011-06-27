package com.akr97.sugerlet;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

import com.akr97.sugerlet.listitem.*;
import com.akr97.sugerlet.model.*;

public class AllContactsActivity extends ContactsActivity {
	@Override
	public String createTitle() {
		return getString(R.string.all_contacts);
	}

	@Override
	public ArrayList<ListItem> createListItems() {
		StructuredNameModel model = new StructuredNameModel(this);
		return createListItems(model.getAll());
	}
	
	public static Intent getIntent(Context context){
		return new Intent(context, AllContactsActivity.class);
	}
}
