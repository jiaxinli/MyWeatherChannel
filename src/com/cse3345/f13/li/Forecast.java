package com.cse3345.f13.li;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cse3345.f13.li.R;
import com.cse3345.f13.li.MainActivity.myLocationListener;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Forecast extends Activity {
	String city;
	String state;
	TextView day1;
	TextView day1Forecast;
	TextView day2;
	TextView day2Forecast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forecast);
		day1 = (TextView) findViewById(R.id.textView2);
		day1Forecast = (TextView) findViewById(R.id.textView3);
		day2 = (TextView) findViewById(R.id.textView4);
		day2Forecast = (TextView) findViewById(R.id.textView5);
		city = getIntent().getExtras().getString("city");
		state = getIntent().getExtras().getString("state");
		new JsonExtractor()
				.execute("http://api.wunderground.com/api/d531d40c6117c8fd/conditions/q/.json");
	}
	
	public void addListenerOnButton() {
		 
		final Context context = this;
 
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context, MainActivity.class);
                            startActivityForResult(intent,1);
                                   		
			}	
		});
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context, Forecast.class);
                            startActivityForResult(intent,1);
                                   		
			}	
		});
	}

	@SuppressWarnings("static-access")
	private class JsonExtractor extends AsyncTask<String, Void, String> {
		JSONObject weather;
		JSONArray forecast;

		@Override
		protected String doInBackground(String... params) {
			Json a = new Json();
			weather = a
					.getJson("http://api.wunderground.com/api/d531d40c6117c8fd/forecast/q/"
							+ city + "," + state + ".json");
			JSONObject weather2 = null;
			JSONObject weather3 = null;
			try {
				weather2 = weather.getJSONObject("forecast");
				weather3 = weather2.getJSONObject("txt_forecast");
				forecast = weather3.getJSONArray("forecastday");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
		}

		@Override
		protected void onPostExecute(String text) {

			try {
				// Log.d("JG",forecast.getJSONObject(0).getString("fcttext"));
				day1.setText(forecast.getJSONObject(0).getString("title") + ":");
				day1Forecast.setText(forecast.getJSONObject(0).getString(
						"fcttext"));
				day2.setText(forecast.getJSONObject(1).getString("title") + ":");
				day2Forecast.setText(forecast.getJSONObject(1).getString(
						"fcttext"));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forecast, menu);
		return true;
	}

}
