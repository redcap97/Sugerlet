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

import java.util.WeakHashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.list_item.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.profile.*;

public class ContactsContentItem  extends ListItem {
	private final Activity activity;
	private final StructuredNameData structuredName;

	private static WeakHashMap<Long, Bitmap> photosCache = new WeakHashMap<Long, Bitmap>();

	private static final int KEY = R.string.key_contacts_content_item;

	public ContactsContentItem(Activity activity, StructuredNameData structuredName) {
		super(Type.CUSTOM);

		this.activity = activity;
		this.structuredName = structuredName;
	}

	@Override
	public View getView(View convertView) {
		ViewHolder holder = getViewHolder(convertView);

		if(convertView == null || holder == null){
			LayoutInflater inflater = LayoutInflater.from(activity);
			convertView = inflater.inflate(R.layout.contact_list_item, null);

			holder = new ViewHolder();
			holder.icon = (ImageView)convertView.findViewById(R.id.imageView);
			holder.name = (TextView)convertView.findViewById(R.id.name);
			holder.phoneticName = (TextView)convertView.findViewById(R.id.phoneticName);

			convertView.setTag(KEY, holder);
		}

		holder.icon.setImageBitmap(getImage());
		holder.name.setText(getName());
		holder.phoneticName.setText(getPhoneticName());

		return convertView;
	}

	public Bitmap getImage(){
		Bitmap bitmap = photosCache.get(structuredName.rawContactId);

		if(bitmap == null){
			PhotoDao photoDao = new PhotoDao(activity);
			PhotoData photo = photoDao.getByRawContactId(structuredName.rawContactId);
			bitmap = photo.getBitmap();

			photosCache.put(structuredName.rawContactId, bitmap);
		}
		return bitmap;
	}

	public String getName(){
		String defaultValue = activity.getString(R.string.no_name_with_mark);
		return structuredName.getName(defaultValue);
	}

	public String getPhoneticName(){
		if(TextUtils.isEmpty(structuredName.getName())){
			EmailDao dao = new EmailDao(activity);
			EmailData firstEmail = dao.getFirst(structuredName.rawContactId);

			if(firstEmail != null){
				String type = activity.getString(R.string.email);
				return String.format("%s: %s", type, firstEmail.address);
			}
		}
		return structuredName.getPhoneticName();
	}

	@Override
	public void onClick(View view){
		Intent intent = ProfileActivity.getIntent(activity, structuredName.rawContactId);
		activity.startActivityForResult(intent, 0);
	}

	private ViewHolder getViewHolder(View convertView){
		if(convertView != null){
			return (ViewHolder)convertView.getTag(KEY);
		}
		return null;
	}

	static class ViewHolder {
		TextView name;
		TextView phoneticName;
		ImageView icon;
	}
}