package com.cse3345.f13.li;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SearchLocation extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_location);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_location, menu);
		return true;
	}

}
