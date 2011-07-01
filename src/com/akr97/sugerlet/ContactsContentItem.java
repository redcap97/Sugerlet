package com.akr97.sugerlet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akr97.sugerlet.listitem.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.profile.ProfileActivity;

public class ContactsContentItem  extends ListItem {
	private final StructuredNameData structuredName;

	private static final int KEY = R.string.key_contacts_content_item;

	public ContactsContentItem(Context context, StructuredNameData structuredName) {
		super(context, Type.CUSTOM);
		this.structuredName = structuredName;
	}

	@Override
	public View getView(View convertView) {
		ViewHolder holder = getViewHolder(convertView);

		if(convertView == null || holder == null){
			LayoutInflater inflater = LayoutInflater.from(this.context);
			convertView = inflater.inflate(R.layout.contact_list_item, null);

			holder = new ViewHolder();
			holder.icon = (ImageView)convertView.findViewById(R.id.imageView1);
			holder.name = (TextView)convertView.findViewById(R.id.name);
			holder.phoneticName = (TextView)convertView.findViewById(R.id.phoneticName);

			convertView.setTag(KEY, holder);
		}

		PhotoDao photoDao = new PhotoDao(context);
		PhotoData photo = photoDao.getByRawContactId(structuredName.rawContactId);

		holder.icon.setImageBitmap(photo.getBitmap());
		holder.name.setText(structuredName.getName());
		holder.phoneticName.setText(structuredName.getPhoneticName());

		return convertView;
	}

	@Override
	public void onClick(View view){
		context.startActivity(ProfileActivity.getIntent(context, structuredName.rawContactId));
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