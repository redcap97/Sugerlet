package com.akr97.sugerlet;

import java.util.ArrayList;

import android.content.Context;

import com.akr97.sugerlet.model.*;

public class StarredContactsGetter extends ContactsGetter {
	public StarredContactsGetter(Context context) {
		super(context);
	}

	@Override
	public String getTitle() {
		return "Starred";
	}

	@Override
	public ArrayList<StructuredNameData> getStructuredNames() {
		StructuredNameModel model = new StructuredNameModel(context);
		return model.getStarred();
	}
}
