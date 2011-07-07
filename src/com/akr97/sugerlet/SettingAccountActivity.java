package com.akr97.sugerlet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

public class SettingAccountActivity extends Activity {
	static final int MENU_SAVE = (Menu.FIRST + 1);
	static final int MENU_RESET = (Menu.FIRST + 2);

	private SettingAccountListAdapter listAdapter;
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_account);

		setTitle(getString(R.string.setting_account));
		setupSettingAccountList();
		setupButtons();
	}

	private void setupSettingAccountList(){
		listAdapter = new SettingAccountListAdapter(this);
		listView = (ListView)findViewById(R.id.listView);
		listView.setAdapter(listAdapter);
		View emptyView = findViewById(R.id.emptyView);
		listView.setEmptyView(emptyView);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id){
				CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBox);
				checkBox.setChecked(!checkBox.isChecked());
			}
		});
	}

	private void setupButtons(){
		View saveButton = findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				saveChanges();
			}
		});

		View cancelButton = findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				cancel();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_SAVE, Menu.NONE, getString(R.string.menu_save));
		menu.add(Menu.NONE, MENU_RESET, Menu.NONE, getString(R.string.menu_reset));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case MENU_SAVE:
			saveChanges();
			return true;
		case MENU_RESET:
			resetChanges();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void saveChanges(){
		listAdapter.save();
		Toast.makeText(this, getString(R.string.message_save), Toast.LENGTH_SHORT).show();
		finish();
	}

	public void resetChanges(){
		listAdapter.reset();
		listAdapter.notifyDataSetChanged();
		listView.invalidateViews();
	}

	public void cancel(){
		finish();
	}

	public static Intent getIntent(Context context){
		return new Intent(context, SettingAccountActivity.class);
	}
}
