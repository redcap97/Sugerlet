package com.akr97.sugerlet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akr97.sugerlet.listitem.*;
import com.akr97.sugerlet.model.*;
import com.akr97.sugerlet.profile.ProfileActivity;

public class ContactListContentItem  extends ListItem {
	StructuredNameData structuredName;

	public ContactListContentItem(Context context, StructuredNameData structuredName) {
		super(context, Type.CUSTOM);
		this.structuredName = structuredName;
	}

	@Override
	public View getView(View convertView) {
		ViewHolder holder;

		if(convertView == null || convertView.getTag() == null){
			LayoutInflater inflater = LayoutInflater.from(this.context);
			convertView = inflater.inflate(R.layout.contact_list_item, null);

			holder = new ViewHolder();
			holder.icon = (ImageView)convertView.findViewById(R.id.imageView1);
			holder.name = (TextView)convertView.findViewById(R.id.name);
			holder.phoneticName = (TextView)convertView.findViewById(R.id.phoneticName);

			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}

		PhotoModel photoModel = new PhotoModel(context);
		PhotoData photo = photoModel.getByRawContactId(structuredName.rawContactId);

		holder.icon.setImageBitmap(photo.getBitmap());
		holder.name.setText(structuredName.getName());
		holder.phoneticName.setText(structuredName.getPhoneticName());

		return convertView;
	}

	@Override
	public void onClick(View view){
		context.startActivity(ProfileActivity.getIntent(context, structuredName.rawContactId));
	}

	static class ViewHolder {
		TextView name;
		TextView phoneticName;
		ImageView icon;
	}
}