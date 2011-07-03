package com.akr97.sugerlet.contacts;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.list_item.*;
import com.akr97.sugerlet.model.*;

public class StarredContactsActivity extends ContactsActivity {

	@Override
	public String createTitle() {
		return getString(R.string.starred_contacts);
	}

	@Override
	public ArrayList<ListItem> createListItems() {
		StructuredNameDao dao = new StructuredNameDao(this);
		return createListItems(dao.getStarred());
	}

	public static Intent getIntent(Context context){
		return new Intent(context, StarredContactsActivity.class);
	}
}
