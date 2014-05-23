package com.plattysoft.leonids.examples;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExampleListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String sampleList[] = new String [] {"One Shot Simple", "One Shot Intermediate", "One Shot Advanced", "Emiter"};
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sampleList));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
		case 0:
			startActivity(new Intent(this, OneShotSimpleExampleActivity.class));
			break;
		case 1:
			startActivity(new Intent(this, OneShotIntermediateExampleActivity.class));
			break;
		case 2:
			startActivity(new Intent(this, OneShotAdvancedExampleActivity.class));
			break;
		}
	}

}
