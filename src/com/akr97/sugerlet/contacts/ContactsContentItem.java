package com.akr97.sugerlet.contacts;

import android.content.Context;
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
		PhotoDao photoDao = new PhotoDao(context);
		PhotoData photo = photoDao.getByRawContactId(structuredName.rawContactId);

		return photo.getBitmap();
	}

	public String getName(){
		String defaultValue = context.getString(R.string.no_name_with_mark);
		return structuredName.getName(defaultValue);
	}

	public String getPhoneticName(){
		if(TextUtils.isEmpty(structuredName.getName())){
			EmailDao dao = new EmailDao(context);
			EmailData firstEmail = dao.getFirst(structuredName.rawContactId);

			if(firstEmail != null){
				String type = context.getString(R.string.email);
				return String.format("%s: %s", type, firstEmail.address);
			}
		}
		return structuredName.getPhoneticName();
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