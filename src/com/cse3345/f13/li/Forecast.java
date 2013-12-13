package com.cse3345.f13.li;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cse3345.f13.li.R;


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
	TextView day11;
	TextView day11Forecast;
	TextView day2;
	TextView day2Forecast;
	TextView day22;
	TextView day22Forecast;
	TextView day3;
	TextView day3Forecast;
	TextView day33;
	TextView day33Forecast;
	TextView day4;
	TextView day4Forecast;
	TextView day44;
	TextView day44Forecast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forecast);
		day1 = (TextView) findViewById(R.id.textView2);
		day1Forecast = (TextView) findViewById(R.id.textView3);
		day11 = (TextView) findViewById(R.id.textView4);
		day11Forecast = (TextView) findViewById(R.id.textView5);
		day2=(TextView) findViewById(R.id.textView6);
		day2Forecast=(TextView) findViewById(R.id.textView7);
		day22=(TextView) findViewById(R.id.textView8);
		day22Forecast=(TextView) findViewById(R.id.textView9);
		day3 = (TextView) findViewById(R.id.textView10);
		day3Forecast = (TextView) findViewById(R.id.textView11);
		day33 = (TextView) findViewById(R.id.textView12);
		day33Forecast = (TextView) findViewById(R.id.textView13);
		day4=(TextView) findViewById(R.id.textView14);
		day4Forecast=(TextView) findViewById(R.id.textView15);
		day44=(TextView) findViewById(R.id.textView16);
		day44Forecast=(TextView) findViewById(R.id.textView17);
		city = getIntent().getExtras().getString("city");
		state = getIntent().getExtras().getString("state");
		new JsonExtractor()
				.execute("http://api.wunderground.com/api/a8893cf69013d65a/conditions/q/.json");
		addListenerOnButton();
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
	}
	
	@SuppressWarnings("static-access")
	private class JsonExtractor extends AsyncTask<String, Void, String> {
		JSONObject weather;
		JSONArray forecast;

		@Override
		protected String doInBackground(String... params) {
			Json a = new Json();
			weather = a
					.getJson("http://api.wunderground.com/api/a8893cf69013d65a/forecast/q/"
							+ city + "," + state + ".json");
			// Log.d("JG",""+ weather.toString());
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
				day11.setText(forecast.getJSONObject(1).getString("title") + ":");
				day11Forecast.setText(forecast.getJSONObject(1).getString(
						"fcttext"));
				day2.setText(forecast.getJSONObject(2).getString("title") + ":");
				day2Forecast.setText(forecast.getJSONObject(2).getString(
						"fcttext"));
				day22.setText(forecast.getJSONObject(3).getString("title") + ":");
				day22Forecast.setText(forecast.getJSONObject(3).getString(
						"fcttext"));
				day3.setText(forecast.getJSONObject(4).getString("title") + ":");
				day3Forecast.setText(forecast.getJSONObject(4).getString(
						"fcttext"));
				day33.setText(forecast.getJSONObject(5).getString("title") + ":");
				day33Forecast.setText(forecast.getJSONObject(5).getString(
						"fcttext"));
				day4.setText(forecast.getJSONObject(6).getString("title") + ":");
				day4Forecast.setText(forecast.getJSONObject(6).getString(
						"fcttext"));
				day44.setText(forecast.getJSONObject(7).getString("title") + ":");
				day44Forecast.setText(forecast.getJSONObject(7).getString(
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
