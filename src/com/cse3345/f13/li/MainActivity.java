package com.cse3345.f13.li;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.final_project.R;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
	private Button mWeather;
	private Button mForecast;
	ImageView mImage;
	Bitmap img = null;
	public static int Forecast_Request;
	TextView mTextView;
	TextView mLocation;
	TextView mLatitude;
	TextView mLongitude;
	TextView mElevation;
	String city;
	String state;
	String Latitude;
	String Longitude;
	String Elevation;
	String icon;
	// the listener to listen to the locations
	private LocationListener listener = null;
	// a location manager
	private LocationManager lm = null;
	// locations instances to GPS and NETWORk
	private Location myLocationGPS, myLocationNetwork;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		myLocationNetwork = lm
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		myLocationGPS = lm
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		listener = new myLocationListener();
		// Log.d("JG", "" + myLocationNetwork.getLatitude());
		// Log.d("JG", "" + myLocationNetwork.getLongitude());
		mImage = (ImageView) findViewById(R.id.imageButton1);
		mWeather = (Button) findViewById(R.id.button1);
		mForecast = (Button) findViewById(R.id.button2);
		mTextView = (TextView) findViewById(R.id.temp);
		mLocation = (TextView) findViewById(R.id.myLocation);
		mElevation = (TextView) findViewById(R.id.elevation);
		mLatitude = (TextView) findViewById(R.id.latitude);
		mLongitude = (TextView) findViewById(R.id.longitude);
		mWeather.setOnClickListener(this);
		mForecast.setOnClickListener(this);
		new JsonExtractor()
				.execute("http://api.wunderground.com/api/a8893cf69013d65a/conditions/q/"
						+ myLocationNetwork.getLatitude()
						+ ","
						+ myLocationNetwork.getLongitude() + ".json");
		// Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
		// Uri.parse("http://maps.google.com/maps"));
		// startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		if (v == mWeather) {
			new JsonExtractor()
					.execute("http://api.wunderground.com/api/a8893cf69013d65a/conditions/q/"
							+ myLocationNetwork.getLatitude()
							+ ","
							+ myLocationNetwork.getLongitude() + ".json");
		}
		if (v == mForecast) {
			// Log.d("JG","mForecast");
			Intent intent = new Intent(this, Forecast.class);
			intent.putExtra("city", city);
			intent.putExtra("state", state);
			intent.putExtra("Latitude", Latitude);
			intent.putExtra("Longitude",Longitude);
			intent.putExtra("Elevation",Elevation);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	public class myLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			// "location" is the RECEIVED locations and its here that you should
			// process it

			// check if the incoming position has been received from GPS or
			// network
			if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				lm.removeUpdates(this);
			} else {
				lm.removeUpdates(listener);
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
			lm.removeUpdates(this);
			lm.removeUpdates(listener);
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}

	private class JsonExtractor extends AsyncTask<String, Void, String> {
		JSONObject weather;
		String Location = "";
		String temp = "0";
		String Latitude = "0";
		String Longitude = "0";
		String Elevation = "0";

		@Override
		protected String doInBackground(String... params) {
			weather = Json
					.getJson("http://api.wunderground.com/api/a8893cf69013d65a/conditions/q/"
							+ myLocationNetwork.getLatitude()
							+ ","
							+ myLocationNetwork.getLongitude() + ".json");
			// Log.d("JG",""+ weather.toString());
			JSONObject currentObservationJson = null;
			JSONObject displayLocationJson = null;
			try {
				currentObservationJson = weather
						.getJSONObject("current_observation");
				displayLocationJson = currentObservationJson
						.getJSONObject("display_location");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			try {
				temp = currentObservationJson.getString("temp_f");
				icon = currentObservationJson.getString("icon_url");
				Location = displayLocationJson.getString("full");
				city = displayLocationJson.getString("city");
				state = displayLocationJson.getString("state");
				Latitude = displayLocationJson.getString("latitude");
				Longitude = displayLocationJson.getString("longitude");
				Elevation = displayLocationJson.getString("elevation");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
		}

		@Override
		protected void onPostExecute(String text) {
			mTextView.setText(temp);
			mLocation.setText("My Location:  " + Location);
			mLatitude.setText("My Latitude: " + Latitude);
			mLongitude.setText("My Longitude:  " + Longitude);
			mElevation.setText("My Elevation:  " + Elevation + " m");
		}

	}


}
