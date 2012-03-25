/*
Copyright (C) 2012 Akira Midorikawa

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.akr97.sugerlet.contacts;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.list_item.*;
import com.akr97.sugerlet.model.*;

public class AllContactsActivity extends ContactsActivity {
	@Override
	public String createTitle() {
		return getString(R.string.all_contacts);
	}

	@Override
	public ArrayList<ListItem> createListItems() {
		StructuredNameDao dao = new StructuredNameDao(this);
		return createListItems(dao.getAll());
	}

	public static Intent getIntent(Context context){
		return new Intent(context, AllContactsActivity.class);
	}
}
