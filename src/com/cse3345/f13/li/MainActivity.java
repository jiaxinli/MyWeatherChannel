package com.cse3345.f13.li;

import com.final_proj.Li.DisplayMessageActivity;
import com.final_proj.Li.MainActivity_twoPlayers;
import com.final_proj.Li.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		System.out.println("Developer: Jiaxin Li");
		return true;
	}
	
	public void addListenerOnButton() {
		 
		final Context context = this;
 
		Button button = (Button) findViewById(R.id.onePlayer);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context, DisplayMessageActivity.class);
                            startActivityForResult(intent,1);
                                   		
			}	
		});
	}

}
