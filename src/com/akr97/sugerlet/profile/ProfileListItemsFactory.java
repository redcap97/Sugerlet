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

package com.akr97.sugerlet.profile;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

import com.akr97.sugerlet.*;
import com.akr97.sugerlet.list_item.*;
import com.akr97.sugerlet.model.*;

public class ProfileListItemsFactory {
	private final Context context;
	private final long rawContactId;

	public ProfileListItemsFactory(Context context, long rawContactId){
		this.context = context;
		this.rawContactId = rawContactId;
	}

	public ArrayList<ListItem> create(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		items.addAll(createPhoneList());
		items.addAll(createEmailList());
		items.addAll(createImList());
		items.addAll(createGroupList());
		items.addAll(createOtherList());
		return items;
	}

	private ArrayList<ListItem> createPhoneList(){
		PhoneDao dao = new PhoneDao(context);
		ArrayList<PhoneData> phones = dao.get(rawContactId);

		ArrayList<ListItem> items = new ArrayList<ListItem>();
		if(!phones.isEmpty()){
			items.add(new ListHeaderItem(context, getString(R.string.header_phone)));

			for(PhoneData phone : phones){
				items.add(new ProfilePhoneItem(context, phone));
			}
		}
		return items;
	}

	private ArrayList<ListItem> createEmailList(){
		EmailDao dao = new EmailDao(context);
		ArrayList<EmailData> emails = dao.get(rawContactId);

		ArrayList<ListItem> items = new ArrayList<ListItem>();
		if(!emails.isEmpty()){
			items.add(new ListHeaderItem(context, getString(R.string.header_email)));

			for(EmailData email : emails){
				String label = dao.getTypeLabel(email);
				Intent intent = new Intent(Intent.ACTION_SENDTO, email.getMailtoUri());

				items.add(new ProfileContentItem(context, label, email.address, intent));
			}
		}
		return items;
	}

	private ArrayList<ListItem> createImList(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		ImDao dao = new ImDao(context);
		ArrayList<ImData> ims = dao.get(rawContactId);
		if(!ims.isEmpty()){
			items.add(new ListHeaderItem(context, getString(R.string.header_im)));
			for(ImData im : ims){
				String protocol = dao.getProtocolLabel(im);
				items.add(new ProfileContentItem(context, protocol, im.data));
			}
		}
		return items;
	}

	private ArrayList<ListItem> createGroupList(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		GroupMembershipDao dao = new GroupMembershipDao(context);
		ArrayList<GroupData> groups = dao.get(rawContactId);

		items.add(new ListHeaderItem(context, getString(R.string.header_group)));
		if(groups.isEmpty()){
			items.add(new ListContentItem(context, getString(R.string.no_group)));
		}else{
			for(GroupData group : groups){
				items.add(new ListContentItem(context, group.title));
			}
		}
		return items;
	}

	private ArrayList<ListItem> createOtherList(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();
		items.addAll(createEventList());
		items.addAll(createWebsiteList());
		items.addAll(createStructuredPostalList());
		items.addAll(createNicknameList());
		items.addAll(createOrganizationList());
		items.addAll(createNoteList());

		if(!items.isEmpty()){
			ArrayList<ListItem> results = new ArrayList<ListItem>();
			results.add(new ListHeaderItem(context, getString(R.string.header_other)));
			results.addAll(items);
			return results;
		}
		return items;
	}

	private ArrayList<ListItem> createStructuredPostalList(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		StructuredPostalDao dao = new StructuredPostalDao(context);
		for(StructuredPostalData postal : dao.get(rawContactId)){
			String label = dao.getTypeLabel(postal);

			items.add(new ProfileContentItem(context, label, postal.formattedAddress));
		}
		return items;
	}

	private ArrayList<ListItem> createNicknameList(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		NicknameDao dao = new NicknameDao(context);
		for(NicknameData nickname : dao.get(rawContactId)){
			String label = getString(R.string.nickname);

			items.add(new ProfileContentItem(context, label, nickname.data));
		}
		return items;
	}

	private ArrayList<ListItem> createWebsiteList(){
		WebsiteDao dao = new WebsiteDao(context);

		ArrayList<ListItem> items = new ArrayList<ListItem>();
		for(WebsiteData website : dao.get(rawContactId)){
			String label = getString(R.string.website);
			Intent intent = new Intent(Intent.ACTION_VIEW, website.getUri());

			items.add(new ProfileContentItem(context, label, website.url, intent));
		}
		return items;
	}

	private ArrayList<ListItem> createEventList(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		EventDao dao = new EventDao(context);
		for(EventData event : dao.get(rawContactId)){
			String label = dao.getTypeLabel(event);

			items.add(new ProfileContentItem(context, label, event.startDate));
		}
		return items;
	}

	private ArrayList<ListItem> createOrganizationList(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		OrganizationDao dao = new OrganizationDao(context);
		for(OrganizationData organization : dao.get(rawContactId)){
			String label = dao.getTypeLabel(organization);
			String content = organization.getDisplayName();

			items.add(new ProfileContentItem(context, label, content));
		}
		return items;
	}

	private ArrayList<ListItem> createNoteList(){
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		NoteDao noteDao = new NoteDao(context);
		for(NoteData note : noteDao.get(rawContactId)){
			String label = getString(R.string.note);

			items.add(new ProfileContentItem(context, label, note.data));
		}
		return items;
	}

	private String getString(int resId) {
		return context.getString(resId);
	}
}
