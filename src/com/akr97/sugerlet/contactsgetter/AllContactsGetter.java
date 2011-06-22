package com.akr97.sugerlet.contactsgetter;

import java.util.ArrayList;

import com.akr97.sugerlet.R;
import com.akr97.sugerlet.model.StructuredNameData;
import com.akr97.sugerlet.model.StructuredNameModel;

import android.content.Context;

public class AllContactsGetter extends ContactsGetter {

	public AllContactsGetter(Context context) {
		super(context);
	}

	@Override
	public String getTitle() {
		return context.getString(R.string.all_contacts);
	}

	@Override
	public ArrayList<StructuredNameData> getStructuredNames() {
		StructuredNameModel model = new StructuredNameModel(context);
		return model.getAll();
	}
}
