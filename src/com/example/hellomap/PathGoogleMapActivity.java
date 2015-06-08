package com.example.hellomap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class PathGoogleMapActivity extends Activity {

	private static final LatLng LOWER_MANHATTAN = new LatLng(40.722543,-73.998585);
	private static final LatLng BROOKLYN_BRIDGE = new LatLng(40.7057, -73.9964);
	private static final LatLng WALL_STREET = new LatLng(40.7064, -74.0094);
	private static final LatLng EMPIRE_BLDG = new LatLng(40.748473, -73.985836);

	GoogleMap googleMap;
	ArrayList<LatLng> latLngArr;
	final String TAG = "PathGoogleMapActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_path_google_map);
		googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
//		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
//				.findFragmentById(R.id.map);
//		googleMap = fm.getMap();
		latLngArr = new ArrayList<LatLng>();
		MarkerOptions options = new MarkerOptions();
		options.position(LOWER_MANHATTAN);
		options.position(BROOKLYN_BRIDGE);
		options.position(WALL_STREET);
		options.position(EMPIRE_BLDG);
		googleMap.addMarker(options);
		latLngArr.add(WALL_STREET);
		latLngArr.add(EMPIRE_BLDG);
//		String url = getMapsApiDirectionsUrl(LOWER_MANHATTAN,BROOKLYN_BRIDGE,latLngArr);
//		ReadTask downloadTask = new ReadTask();
//		downloadTask.execute(url);
//
//		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BROOKLYN_BRIDGE,13));
//		addMarkers();

	}

	private String getMapsApiDirectionsUrl(LatLng ori,LatLng dest, ArrayList<LatLng> wayPointsLst) {
		String orgin = "origin="+ori.latitude + "," + ori.longitude;
		String destination = "destination="+dest.latitude + ","+ dest.longitude;
		String sensor = "sensor=false";
		String output = "json";
		String wayPoints = genWayPointsPara(wayPointsLst);
		String optimizeMode = "&waypoints=optimize:false";
		if (!wayPoints.equals("")){
			wayPoints =  optimizeMode+ wayPoints;
		} 		
		String params = orgin+ "&"+ destination + wayPoints +"&" + sensor;
		
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + params;
		Log.d(TAG,"request url: "+url);
		return url;
	}
	
	private String genWayPointsPara(ArrayList<LatLng> wayPointsLst){
		StringBuilder wayPointsParaSb = new StringBuilder();
		for (LatLng latLng : wayPointsLst){
			wayPointsParaSb.append("|").append(latLng.latitude).append(",").append(latLng.longitude);
		}
		Log.d(TAG,"wayPointPara: "+wayPointsParaSb.toString());
		return wayPointsParaSb.toString();
	}
	
	private void addMarkers() {
		if (googleMap != null) {
			googleMap.addMarker(new MarkerOptions().position(BROOKLYN_BRIDGE)
					.title("First Point"));
			googleMap.addMarker(new MarkerOptions().position(LOWER_MANHATTAN)
					.title("Second Point"));
			googleMap.addMarker(new MarkerOptions().position(WALL_STREET)
					.title("Third Point"));
		}
	}

	private class ReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... url) {
			String data = "";
			try {
				HttpConnection http = new HttpConnection();
				data = http.readUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			new ParserTask().execute(result);
		}
	}

	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				PathJSONParser parser = new PathJSONParser();
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
			ArrayList<LatLng> points = null;
			PolylineOptions polyLineOptions = null;

			// traversing through routes
			for (int i = 0; i < routes.size(); i++) {
				points = new ArrayList<LatLng>();
				polyLineOptions = new PolylineOptions();
				List<HashMap<String, String>> path = routes.get(i);

				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				polyLineOptions.addAll(points);
				polyLineOptions.width(5);
				polyLineOptions.color(Color.BLUE);
			}

			googleMap.addPolyline(polyLineOptions);

		}
	}
	
	private class TextRouteTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... url) {
			String data = "";
			try {
				HttpConnection http = new HttpConnection();
				data = http.readUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			new TextParserTask().execute(result);
		}
	}

	private class TextParserTask extends AsyncTask<String, Integer, List<List<String>>> {

		@Override
		protected List<List<String>> doInBackground(String... jsonData) {

			JSONObject jObject;
			List<List<String>> textNavigations=new ArrayList<List<String>>();

			try {
				jObject = new JSONObject(jsonData[0]);
				PathTextJSONParser tPaser = new PathTextJSONParser();
				textNavigations = tPaser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return textNavigations;
		}
		
		@Override
		protected void onPostExecute(List<List<String>> textNavigations){
			Intent intent = new Intent(PathGoogleMapActivity.this,ShowTextPathActivity.class);
			List<String> textDistances = textNavigations.get(0);
			List<String> textDirections = textNavigations.get(1);
			intent.putStringArrayListExtra("distanceTextArrayLst", (ArrayList<String>) textDistances);
			intent.putStringArrayListExtra("pathTextArrayLst", (ArrayList<String>) textDirections);
			PathGoogleMapActivity.this.startActivity(intent);
		}
			
	}
	public void showPath(View view){
		int id = view.getId();

		if (id == R.id.btnPath) {
			String url = getMapsApiDirectionsUrl(LOWER_MANHATTAN,BROOKLYN_BRIDGE,latLngArr);
			ReadTask downloadTask = new ReadTask();
			downloadTask.execute(url);
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BROOKLYN_BRIDGE,13));
			addMarkers();
			
		} else if (id == R.id.btnPathText) {
			String url = getMapsApiDirectionsUrl(LOWER_MANHATTAN,BROOKLYN_BRIDGE,latLngArr);
			TextRouteTask downloadTask = new TextRouteTask();
			downloadTask.execute(url);
		}
	}
}