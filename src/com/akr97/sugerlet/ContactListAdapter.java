package com.akr97.sugerlet;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.Context;

import com.akr97.sugerlet.model.*;

public class ContactListAdapter extends BaseAdapter {
	private ArrayList<StructuredNameData> structuredNames;
	private Context context;
	
	static final String TAG = "com.akr97.sugerlet.ContactListAdapter";

	public ContactListAdapter(Context context, ArrayList<StructuredNameData> sns) {
		this.structuredNames = sns;
		this.context = context;
	}

	@Override
	public int getCount() {
		return structuredNames.size();
	}

	@Override
	public Object getItem(int position) {
		return structuredNames.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		StructuredNameData structuredName = (StructuredNameData)getItem(position);
		ViewHolder holder;
		
		if(convertView == null){
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
	
	static class ViewHolder {
	    TextView name;
	    TextView phoneticName;
	    ImageView icon;
	}
}